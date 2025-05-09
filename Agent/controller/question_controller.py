from fastapi import APIRouter
from agent.google_agent import stream_agent
from model.query import Query 


router = APIRouter()

@router.post("/ask/")
async def ask_question(query: Query):
    try:
        steps = stream_agent([{"role": "user", "content": query.prompt}]
)
        final_message = ""
        for step in steps:
            final_message = step["messages"][-1].content
        return {"answer": final_message}
    except Exception as e:
        return {"error": str(e)}