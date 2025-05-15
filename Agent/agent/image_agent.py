from google.genai import types
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

def generate_image_bytes(prompt: str) -> tuple[bytes, str]:
    response = client.models.generate_content(
        model="gemini-2.0-flash-preview-image-generation", 
        contents=prompt,
        config=types.GenerateContentConfig(
        response_modalities=['TEXT', 'IMAGE']
    )
        
    )

    if response.candidates and response.candidates[0].content.parts:
        for part in response.candidates[0].content.parts:
            if hasattr(part, 'inline_data') and part.inline_data and part.inline_data.data:
                image_bytes = part.inline_data.data 
                mime_type = part.inline_data.mime_type
                return image_bytes, mime_type
    raise Exception("Nenhuma imagem foi gerada pelo modelo do Google.")

# def generate_image_base64(prompt: str) -> str:

#     response = client.models.generate_content(
#     model="gemini-2.0-flash-preview-image-generation",
#     contents=prompt,
#     config=types.GenerateContentConfig(
#       response_modalities=['TEXT', 'IMAGE']
#     )
# )

#     for part in response.candidates[0].content.parts:
#         if part.inline_data is not None:
#             image = Image.open(BytesIO(part.inline_data.data))
#             buffered = BytesIO()
#             image.save(buffered, format="PNG")
#             img_base64 = base64.b64encode(buffered.getvalue()).decode("utf-8")
#             return img_base64

#     raise Exception("Nenhuma imagem foi gerada pelo modelo.")
