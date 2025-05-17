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

        print("Final message:", final_message)
        return final_message
    
    def generate_image_by_text(self, prompt: str):
        """
        Generate an image from text using the LLM agent.
        """

        image_agent = ImageAgent().generate_model()
        response = image_agent.models.generate_content(
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
        raise HTTPException("Nenhuma imagem foi gerada pelo modelo do Google.")
    