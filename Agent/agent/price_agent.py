# import os
# from dotenv import load_dotenv
# # from langchain.chat_models import init_chat_model
# from langchain_google_genai import ChatGoogleGenerativeAI
# from langchain_tavily import TavilySearch
# from langchain_core.prompts import ChatPromptTemplate
# from langchain.agents import AgentExecutor
# # from langgraph.prebuilt import create_react_agent

# # Carrega variáveis de ambiente
# load_dotenv()

# # Lê e define as chaves
# tavily_key = os.getenv("TAVILY_API_KEY")
# google_key = os.getenv("GOOGLE_API_KEY")

# if not tavily_key or not google_key:
#     raise RuntimeError("Chaves de API não definidas no .env")

# os.environ["TAVILY_API_KEY"] = tavily_key
# os.environ["GOOGLE_API_KEY"] = google_key

# json_schema = {
#     "title": "book title",
#     "author": "book author",
#     "price" : "book price",
#     "link" : "store link to book",
#     "type": "object",
#     "properties": {
#         "title": {
#             "type": "string",
#             "description": "The title of the book",
#         },
#         "author": {
#             "type": "string",
#             "description": "The author to the book",
#         },
#         "price": {
#             "type": "double",
#             "description": "The price of the book",
#             "default": None,
#         },
#         "link": {
#             "type": "string",
#             "description": "The link to the store",
#             "default": None,
#         },
#     },
#     "required": ["title", "price", "link"],
# }

# # Inicializa o modelo e a ferramenta
# tavily_tool = TavilySearch(max_results=5, topic="general")

# # Cria o modelo de prompt
# prompt = ChatPromptTemplate.from_messages(
#     [
#         (
#             "system",
#             "You are a helpful assistant that search for prices of selected books in the internet and return the information to the user. You search for 5 prices. Format the response in JSON formart with these fields: title, author, [price,  link]. Return empty array if book offers not found"
#             ,
#         ),
#         ("human", "{input}"),
#     ]
# )

# # Inicializa o modelo de linguagem
# llm = ChatGoogleGenerativeAI(
#     model="gemini-2.0-flash",
#     temperature=0,
#     max_tokens=None,
#     timeout=None,
#     max_retries=2)

# llm.with_structured_output(schema=json_schema)
# llm.bind_tools([tavily_tool])

# agent = (prompt | llm)

# # Cria o executor do agente
# agent_executor = AgentExecutor(agent=agent, tools=[tavily_tool], verbose=True)


# def get_price_agent():
#     return agent_executor


from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder
from langchain.agents import AgentExecutor, create_tool_calling_agent
from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_tavily import TavilySearch
import os
from dotenv import load_dotenv

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
    ("system", "You are a helpful assistant that searches book prices on the internet. Return 5 offers that you find on internet. Only consider offers that has a price associated with it and are shown as R$ [value] and organize the response a in JSON format like this: title, author, price, link. Return empty array if not found."),
    ("human", "{input}"),
    MessagesPlaceholder(variable_name="agent_scratchpad")
])

# Define schema de output (OBS: "author", não "autor")
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
