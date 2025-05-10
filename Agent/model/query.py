from pydantic import BaseModel

class Query(BaseModel):
    prompt: str