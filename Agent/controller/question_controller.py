from fastapi import APIRouter
from agent.price_agent import get_price_agent
from agent.image_agent import generate_image_base64

from model.query import Query 


router = APIRouter()


@router.post("/ask/")
async def ask_question(query: Query):
    
    try:
        agent = get_price_agent()
    
        final_message = agent.invoke({"input": query.prompt})
        
        return {"answer": final_message}
    except Exception as e:
        return {"error": str(e)}
    
@router.post("/generate-image-from-text/")
async def generate_image_by_text(request: Query):
    try:
        image = generate_image_base64(request.prompt)
        return {"imageBase64": image}
    except Exception as e:
        return {"error": str(e)}