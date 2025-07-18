import google.generativeai as genai
from io import BytesIO
import base64
import os
from dotenv import load_dotenv

# Carrega variáveis de ambiente
load_dotenv()

google_api_key = os.getenv("GOOGLE_API_KEY")

if not google_api_key:
    raise RuntimeError("Chave de API google não definida no .env")

genai.configure(api_key=google_api_key)

class ImageAgent:
    def __init__(self):
        self.model = genai.GenerativeModel("gemini-pro-vision")

    def generate_model(self):
        return self.model
