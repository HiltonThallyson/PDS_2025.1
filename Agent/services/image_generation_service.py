from fastapi import HTTPException
from google.genai import types
from agent.facades.llm_agents_facade import LLMAgentsFacade
from agent.image_agent import ImageAgent


# class ImageGenerationService:
#     def generate_image_bytes(self, prompt: str) -> tuple[bytes, str]:
        
#         try:
#             llm_agents_facade = LLMAgentsFacade()
#             image_bytes, mime_type = llm_agents_facade.generate_image_by_text(prompt)
#             return image_bytes, mime_type
#         except HTTPException as e:
#             raise HTTPException(
#                 status_code=500,
#                 detail=f"Erro ao gerar imagem"
#             )

class ImageGenerationService:
    def generate_image_bytes(self, prompt: str) -> tuple[bytes, str]:
        print("--- DENTRO DO ImageGenerationService ---")
        print(f"Prompt recebido para gerar imagem: '{prompt}'")
        
        try:
            llm_agents_facade = LLMAgentsFacade()
            image_bytes, mime_type = llm_agents_facade.generate_image_by_text(prompt)

            # --- LOGS DE DEPURAÇÃO ADICIONADOS ---
            print(f"MIME type retornado pela facade: {mime_type}")
            
            if image_bytes:
                print(f"Bytes retornados pela facade. Tamanho: {len(image_bytes)} bytes.")
                # Imprimir os primeiros 100 bytes pode nos dar uma pista do conteúdo
                print(f"Cabeçalho dos bytes (primeiros 100): {image_bytes[:100]}") 
            else:
                print("ERRO: A facade retornou 'image_bytes' como None ou vazio!")
            
            print("--- FIM DO ImageGenerationService ---")
            return image_bytes, mime_type
        
        except Exception as e:
            # Este log é crucial para ver o erro completo no console
            print("Uma exceção ocorreu ao tentar gerar a imagem na facade!")
            raise HTTPException(
                status_code=500,
                detail=f"Erro ao gerar imagem: {str(e)}"
            )
        