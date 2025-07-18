from fastapi import HTTPException
from agent.facades.llm_agents_facade import LLMAgentsFacade
from agent.image_agent import ImageAgent




class ImageGenerationService:
    def generate_image_bytes(self, prompt: str) -> tuple[bytes, str]:
        
        try:
            llm_agents_facade = LLMAgentsFacade()
            image_bytes, mime_type = llm_agents_facade.generate_image_by_text(prompt)

            
            
            return image_bytes, mime_type
        
        except Exception as e:
            raise HTTPException(
                status_code=500,
                detail=f"Erro ao gerar imagem"
            )
        