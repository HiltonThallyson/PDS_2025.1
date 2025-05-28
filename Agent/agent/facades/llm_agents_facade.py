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

        print("Final message:", final_message)
        return final_message
    
    # def generate_image_by_text(self, prompt: str) -> tuple[bytes, str]:
    #     """
    #     Generate an image from text using the LLM agent.
    #     """

    #     image_agent = ImageAgent().generate_model()
    #     response = image_agent.models.generate_content(
    #         model="gemini-2.0-flash-preview-image-generation", 
    #         contents=prompt,
    #         config=types.GenerateContentConfig(
    #         response_modalities=['TEXT', 'IMAGE']
    #     )
            
    #     )

    #     if response.candidates and response.candidates[0].content.parts:
    #         for part in response.candidates[0].content.parts:
    #             if hasattr(part, 'inline_data') and part.inline_data and part.inline_data.data:
    #                 mime_type = part.inline_data.mime_type
    #                 image_bytes = part.inline_data.data
    #                 return image_bytes, mime_type
    #     raise HTTPException("Nenhuma imagem foi gerada pelo modelo do Google.")
    def generate_image_by_text(self, prompt: str) -> tuple[bytes, str]:
        """
        Generate an image from text using the LLM agent, now with robust debugging.
        """
        print("\n--- [FACADE] Iniciando a geração de imagem ---")
        print(f"[FACADE] Prompt: '{prompt}'")

        try:
            image_agent = ImageAgent().generate_model()
            
            # A chamada para a API. Mantendo seus parâmetros.
            response = image_agent.models.generate_content(
                model="gemini-2.0-flash-preview-image-generation", 
                contents=prompt,
                config=types.GenerateContentConfig(response_modalities=['TEXT', 'IMAGE'])
                # Removi o 'config' para simplificar a chamada. Se ele for essencial,
                # a depuração da resposta completa abaixo nos dirá.
            )

            # --- ETAPA DE DEPURAÇÃO MAIS IMPORTANTE ---
            # Vamos imprimir a resposta COMPLETA que recebemos do Google.
            # Aqui descobriremos a verdade.
            print("--------------------------------------------------")
            print(f"[FACADE DEBUG] RESPOSTA COMPLETA DA API DO GOOGLE:\n{response}")
            print("--------------------------------------------------")

            # Verificação se a resposta tem a estrutura esperada
            if not response.candidates:
                print("[FACADE ERRO] A resposta da API não contém 'candidates'.")
                # O print acima mostrará o motivo (erro de bloqueio, etc.)
                raise HTTPException(status_code=500, detail="A API do Google não retornou candidatos. A requisição pode ter sido bloqueada por segurança.")

            # Itera pelas partes da resposta
            for part in response.candidates[0].content.parts:
                print(f"[FACADE DEBUG] Analisando 'part' da resposta: {part}")

                # Se a API retornar um texto (provavelmente um erro), vamos logar.
                if hasattr(part, 'text') and part.text:
                    print(f"[FACADE AVISO] A API retornou uma parte de TEXTO: '{part.text}'")

                # Verifica se a parte contém os dados da imagem
                if hasattr(part, 'inline_data') and part.inline_data and part.inline_data.data:
                    print("[FACADE INFO] Encontrada 'inline_data' com dados.")
                    
                    image_bytes = part.inline_data.data
                    mime_type = part.inline_data.mime_type
                    
                    # Tenta decodificar os dados, agora com tratamento de erro
                    try:
                        
                        print(f"[FACADE INFO] Decodificação de Base64 teve sucesso. Tamanho dos bytes: {len(image_bytes)}.")
                        # Retorna os dados SOMENTE se a decodificação funcionar
                        return image_bytes, mime_type
                    except Exception as e:
                        print(f"[FACADE ERRO] FALHA AO DECODIFICAR BASE64! Erro: {e}")
                        print(f"[FACADE ERRO] Os dados recebidos não são Base64. Conteúdo: {base64_data}")
                        # Não lança erro aqui, continua o loop para o caso de haver outra parte válida
            
            # Se o loop terminar e nenhuma imagem for retornada
            print("[FACADE ERRO] Nenhuma parte da resposta continha uma imagem válida.")
            raise HTTPException(status_code=500, detail="Nenhuma imagem válida foi encontrada na resposta do Google após análise.")

        except Exception as e:
            # Pega qualquer outro erro que possa acontecer
            print("[FACADE ERRO CRÍTICO] Uma exceção inesperada ocorreu na facade!")
            raise HTTPException(status_code=500, detail=f"Erro crítico na facade: {str(e)}")

    