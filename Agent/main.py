from fastapi import FastAPI
from controller.question_controller import router as question_router

app = FastAPI()

app.include_router(question_router, prefix="/api")

@app.get("/")
def root():
    return {"message": "Welcome to the API!"}


