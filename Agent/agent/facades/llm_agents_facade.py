import base64
import re
from google.genai import types
from fastapi import HTTPException, Response
from agent.price_agent import get_price_agent
from agent.image_agent import ImageAgent
from services.format_model_response_service import format_model_response


class LLMAgentsFacade:
    def search_price(self, prompt: str):
        """
        Search for the price of a product using the LLM agent.
        """
        agent = get_price_agent()

        final_message = agent.invoke({"input": prompt})
        final_message = format_model_response(final_message["output"])

        return final_message
    
    
    def generate_image_by_text(self, prompt: str) -> tuple[bytes, str]:
        """
        Generate an image from text using the LLM agent, now with robust debugging.
        """

        try:
            image_agent = ImageAgent().generate_model()
            
            response = image_agent.models.generate_content(
                model="gemini-2.0-flash-preview-image-generation", 
                contents=prompt,
                config=types.GenerateContentConfig(response_modalities=['TEXT', 'IMAGE'])
            )

            if not response.candidates:
                raise HTTPException(status_code=500, detail="A API do Google não retornou candidatos. A requisição pode ter sido bloqueada por segurança.")

            for part in response.candidates[0].content.parts:
                if hasattr(part, 'inline_data') and part.inline_data and part.inline_data.data:
                    image_bytes = part.inline_data.data
                    mime_type = part.inline_data.mime_type
                    
                    try:
                        
                        return image_bytes, mime_type
                    except Exception as e:
                        raise HTTPException(status_code=500, detail="Erro ao processar os dados da imagem retornada pelo Google.")
            raise HTTPException(status_code=500, detail="Nenhuma imagem válida foi encontrada na resposta do Google após análise.")

        except Exception as e:
            raise HTTPException(status_code=500, detail=f"Erro crítico")

    