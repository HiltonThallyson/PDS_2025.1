from fastapi import HTTPException
from google.generativeai import types
from google import genai
from PIL import Image
from io import BytesIO
import base64
import os
from dotenv import load_dotenv

# Carrega variáveis de ambiente
load_dotenv()

google_api_key = os.getenv("GOOGLE_API_KEY")
os.environ["GOOGLE_API_KEY"] = google_api_key

if not google_api_key:
    raise RuntimeError("Chave de API google não definida no .env")

client = genai.Client(api_key=google_api_key)

class ImageAgent:
    def __init__(self):
        self.client = genai.Client(api_key=google_api_key)

    def generate_model(self):
        return client
        

