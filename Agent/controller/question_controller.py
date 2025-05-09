from fastapi import APIRouter
from agent.google_agent import get_agent

from model.query import Query 


router = APIRouter()


@router.post("/ask/")
async def ask_question(query: Query):
    
    try:
        agent = get_agent()
    
        final_message = agent.invoke({"input": query.prompt})
        
        return {"answer": final_message}
    except Exception as e:
        return {"error": str(e)}