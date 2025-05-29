import json
from fastapi import APIRouter, HTTPException, Response
from services.image_generation_service import ImageGenerationService
from services.price_search_service import PriceSearchService 

from model.query import Query


router = APIRouter()


@router.post("/search_price")
async def ask_question(query: Query):
    prompt = f"Find 5 offers for the book: {query.prompt} on the internet. Return 5 offers that you find on internet."
    try:
        price_search_service = PriceSearchService()
        final_message = price_search_service.search_prices(prompt)

        return json.loads(final_message)
    except HTTPException as e:
        return {"Erro ao tentar buscar ofertas"}
    

@router.post("/generate-image-from-text")
async def generate_image_by_text_raw_bytes(request: Query): 
    try:
       
        image_bytes, mime_type = ImageGenerationService().generate_image_bytes(request.prompt)

        if not image_bytes or not mime_type:
            raise HTTPException(
                status_code=500,
                detail="Falha ao gerar dados da imagem a partir do provedor."
            )

        return Response(content=image_bytes, media_type=mime_type)

    except HTTPException as http_exc:
        raise http_exc
    except Exception as e:
        raise HTTPException(
            status_code=500,
            detail=f"Erro interno ao gerar imagem: {str(e)}"
        )