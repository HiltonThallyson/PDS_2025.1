from fastapi import HTTPException
from agent.facades.llm_agents_facade import LLMAgentsFacade


class PriceSearchService:
    def search_prices(self, prompt: str):
        try:
            llm_agents_facade = LLMAgentsFacade()
            response = llm_agents_facade.search_price(prompt)
            return response
        except HTTPException as e:
            raise HTTPException(
                status_code=500,
                detail=f"Erro ao buscar ofertas"
            )