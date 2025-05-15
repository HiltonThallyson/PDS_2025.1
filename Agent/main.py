from fastapi import FastAPI
from controller.question_controller import router as question_router
from fastapi.middleware.cors import CORSMiddleware



app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"], 
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(question_router, prefix="/api")

@app.get("/")
def root():
    return {"message": "Welcome to the API!"}


