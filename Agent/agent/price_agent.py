from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder
from langchain.agents import AgentExecutor, create_tool_calling_agent
from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_tavily import TavilySearch
import os
from dotenv import load_dotenv
import re
import json

# Carrega variáveis de ambiente
load_dotenv()

tavily_key = os.getenv("TAVILY_API_KEY")
google_key = os.getenv("GOOGLE_API_KEY")

if not tavily_key or not google_key:
    raise RuntimeError("Chaves de API não definidas no .env")

# Configura as ferramentas
tavily_tool = TavilySearch(max_results=5, topic="general")

# Cria o prompt
prompt = ChatPromptTemplate.from_messages([
    ("system", "You are a helpful assistant that searches book prices on the internet and return 5 offers that you find on internet. You only consider offers in Brazil that has a price associated with it and the price has value in R$. Dont consider offers with price null. If the offer have multiple prices, pick the lowest one. Get the offers and format the response organizing it as JSON using these keys: title, author, price, link, imageUrl. Return empty array if not found."),
    ("human", "{input}"),
    MessagesPlaceholder(variable_name="agent_scratchpad")
])

json_schema = {
    "title": "book title",
    "author": "book author",
    "price": "book price",
    "link": "store link to book",
    "type": "object",
    "properties": {
        "title": {"type": "string", "description": "The title of the book"},
        "author": {"type": "string", "description": "The author of the book"},
        "price": {"type": "number", "description": "The price of the book"},
        "link": {"type": "string", "description": "Link to the store"},
    },
    "required": ["title", "price", "link"],
}

# Inicializa o modelo com ferramenta
llm = ChatGoogleGenerativeAI(
    model="gemini-2.0-flash",
    temperature=0,
    max_tokens=None,
)

# Conecta ferramenta e prompt ao modelo
agent = create_tool_calling_agent(llm, [tavily_tool], prompt)

# Executor do agente
agent_executor = AgentExecutor(agent=agent, tools=[tavily_tool], verbose=True)

def get_price_agent():
    return agent_executor

