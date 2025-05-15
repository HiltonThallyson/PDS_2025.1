import json
from fastapi import APIRouter, HTTPException, Response
from agent.price_agent import get_price_agent
from agent.image_agent import generate_image_bytes
from services.format_model_response_service import format_model_response

from model.query import Query 


router = APIRouter()


@router.post("/search_price")
async def ask_question(query: Query):
    prompt = f"Find 5 offers for the book: {query.prompt} on the internet. Return 5 offers that you find on internet."
    try:
        agent = get_price_agent()

        final_message = agent.invoke({"input": prompt})
        final_message = format_model_response(final_message["output"])

        print("Final message:", final_message)
        

        return json.loads(final_message)
    except Exception as e:
        return {"error": str(e)}
    
# @router.post("/generate-image-from-text")
# async def generate_image_by_text(request: Query):
#     try:
#         image = generate_image_base64(request.prompt)
#         return {"image_base64": image}

#     except Exception as e:
#         return {"error": str(e)}
@router.post("/generate-image-from-text")
async def generate_image_by_text_raw_bytes(request: Query): # Nome do método pode ser alterado para clareza
    try:
        # 1. Chamar a função do agente para obter os bytes brutos e o tipo MIME
        image_bytes, mime_type = generate_image_bytes(request.prompt)

        if not image_bytes or not mime_type:
            # A função do agente deve levantar exceção se não conseguir gerar,
            # mas uma verificação aqui é uma boa prática.
            raise HTTPException(
                status_code=500,
                detail="Falha ao gerar dados da imagem a partir do provedor."
            )

        # 2. Retornar os bytes brutos da imagem usando fastapi.responses.Response
        # O FastAPI definirá o header Content-Type automaticamente com base no media_type.
        return Response(content=image_bytes, media_type=mime_type)

    except HTTPException as http_exc:
        # Re-levantar exceções HTTP para que o FastAPI as manipule corretamente
        raise http_exc
    except Exception as e:
        # Para outros tipos de exceções, logar e retornar um erro 500
        # O FastAPI, por padrão, retornará uma resposta JSON para HTTPException.
        print(f"Erro inesperado ao gerar imagem: {e}") # Logar o erro
        raise HTTPException(
            status_code=500,
            detail=f"Erro interno ao gerar imagem: {str(e)}"
        )