import os
from dotenv import load_dotenv
from langchain.chat_models import init_chat_model
from langchain_tavily import TavilySearch
from langgraph.prebuilt import create_react_agent

# Carrega variáveis de ambiente
load_dotenv()

# Lê e define as chaves
tavily_key = os.getenv("TAVILY_API_KEY")
google_key = os.getenv("GOOGLE_API_KEY")

if not tavily_key or not google_key:
    raise RuntimeError("Chaves de API não definidas no .env")

os.environ["TAVILY_API_KEY"] = tavily_key
os.environ["GOOGLE_API_KEY"] = google_key

# Inicializa o modelo e a ferramenta
llm = init_chat_model("gemini-2.0-flash", model_provider="google_genai")
tavily_tool = TavilySearch(max_results=5, topic="general")

# Cria o agente
agent = create_react_agent(llm, [tavily_tool])

def stream_agent(messages: str):
    return agent.stream({"messages": messages}, stream_mode="values")
