// package br.imd.mybookplace.services;

// import br.imd.mybookplace.DTOS.LivroDTO;
// import br.imd.mybookplace.exceptions.GoogleBooksApiException;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Service;
// import org.springframework.web.reactive.function.client.WebClient;
// import org.springframework.web.reactive.function.client.WebClientRequestException;
// import org.springframework.web.reactive.function.client.WebClientResponseException;
// import org.springframework.web.util.UriComponentsBuilder;


// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.List;
// import java.util.Map;

// /**
//  * Serviço responsável por consumir a API do Google Books e converter os resultados para LivroDTO.
//  */
// @Service
// public class GoogleBookService {
//    private final WebClient webClient;
//    private static final Logger logger = LoggerFactory.getLogger(GoogleBookService.class);

//     public GoogleBookService(WebClient.Builder builder) {
//         this.webClient = builder.baseUrl("https://www.googleapis.com/books/v1").build();
//     }

//     /**
//      * Busca livros pelo título informado.
//      *
//      * @param titulo Título do livro a ser pesquisado.
//      * @return Lista de livros encontrados.
//      * @throws GoogleBooksApiException em caso de erro na comunicação com a API do Google Books.
//      */
//     public List<LivroDTO> buscarLivrosPorTitulo(String titulo) {
//         String url = construirUrl("intitle", titulo, null);
//         return buscarLivros(url);
//     }

//     /**
//      * Busca livros pelo nome do autor informado.
//      *
//      * @param autor Nome do autor a ser pesquisado.
//      * @return Lista de livros encontrados.
//      * @throws GoogleBooksApiException em caso de erro na comunicação com a API do Google Books.
//      */
//     public List<LivroDTO> buscarLivrosPorAutor(String autor) {
//         String url = construirUrl("inauthor", autor, null);

//         return buscarLivros(url);
//     }

//     /**
//      * Busca livros de várias categorias, limitando a quantidade por categoria.
//      *
//      * @param qtdPorCategoria Quantidade máxima de livros por categoria.
//      * @return Lista de livros encontrados em todas as categorias.
//      * @throws GoogleBooksApiException em caso de erro na comunicação com a API do Google Books.
//      */
//     public List<LivroDTO> buscarLivrosPorQuantidade(int qtdPorCategoria) {
//         String [] categorias = {"history", "science", "technology", "art", "fiction"};
//         List<LivroDTO> livros = new ArrayList<>();

//         for (String categoria : categorias) {
//             String url = construirUrl("subject", categoria, qtdPorCategoria);

//             livros.addAll(buscarLivros(url));
//         }

//         return livros;
//     }

//     /**
//      * Busca livros por uma categoria específica.
//      *
//      * @param categoria Categoria a ser pesquisada.
//      * @return Lista de livros encontrados na categoria.
//      * @throws GoogleBooksApiException em caso de erro na comunicação com a API do Google Books.
//      */
//     public List<LivroDTO> buscarLivrosPorCategoria(String categoria){
//         String url = construirUrl("subject", categoria, null);
   
//         return buscarLivros(url);
//     }

//     /**
//      * Busca livros pelo ISBN informado.
//      *
//      * @param isbn ISBN do livro a ser pesquisado.
//      * @return Lista de livros encontrados com o ISBN informado.
//      * @throws GoogleBooksApiException em caso de erro na comunicação com a API do Google Books.
//      */
//     public List<LivroDTO> buscarLivrosPorISBN(String isbn) {
//         String url = construirUrl("isbn", isbn, 1);
//         return buscarLivros(url);
//     }

//     /**
//      * Realiza a requisição à API do Google Books e retorna o resultado como mapa.
//      *
//      * @param url URL já montada para consulta na API.
//      * @return Mapa com os dados retornados pela API.
//      * @throws GoogleBooksApiException em caso de erro na comunicação ou resposta inesperada da API.
//      */
//     private Map<String, Object> fazerRequisicaoAPIGoogle(String url){
//         try{
//             Map<String, Object> response = webClient.get()
//             .uri(url)
//             .retrieve()
//             .bodyToMono(Map.class)
//             .block(); // .block() para obter resultado síncrono

//             if (response == null || response.get("items") == null) {
//                 return Collections.emptyMap(); // Retorna lista vazia se não houver resultado
//             }
    
//             return response;
//         } catch (WebClientResponseException | WebClientRequestException e) {
//             logger.error("Erro ao acessar a API do Google Books: {}", e.getMessage());
//             throw new GoogleBooksApiException("Erro ao acessar a API do Google Books:" + e.getMessage(), e);
//         } catch (Exception e) {
//             logger.error("Erro inesperado na API do Google Books: {}", e.getMessage());
//             throw new GoogleBooksApiException("Erro inesperado: " + e.getMessage(), e);
//         }
        
//     }

//     /**
//      * Converte a lista de itens da API do Google Books para uma lista de LivroDTO.
//      *
//      * @param items Lista de mapas representando os livros retornados pela API.
//      * @return Lista de LivroDTO.
//      */
//     private List<LivroDTO> converterParaLivros(List<Map<String, Object>> items){
//         List<LivroDTO> livros = new ArrayList<>();

//         for(Map<String, Object> item: items){
//             Map<String, Object> volumeInfo = (Map<String, Object>) item.get("volumeInfo");

//             LivroDTO dto = new LivroDTO();
//             dto.setTitle((String) volumeInfo.get("title"));
//             dto.setSubtitle((String) volumeInfo.get("subtitle"));
//             dto.setAuthors((List<String>) volumeInfo.get("authors"));
//             dto.setEditora((String) volumeInfo.get("publisher"));
//             dto.setDescription((String) volumeInfo.get("description"));
//             dto.setCategories((List<String>) volumeInfo.get("categories"));

//             Map<String, String> imageLinks = (Map<String, String>) volumeInfo.get("imageLinks");
//             if (imageLinks != null) {
//                 dto.setThumbnail(imageLinks.get("thumbnail"));
//             }

//             // Extrair ISBN
//             List<Map<String, String>> industryIdentifiers = (List<Map<String, String>>) volumeInfo.get("industryIdentifiers");
//             if (industryIdentifiers != null) {
//                 String isbn13 = null;
//                 String isbn10 = null;
//                 for (Map<String, String> identifierMap : industryIdentifiers) {
//                     if (identifierMap != null) {
//                         String type = identifierMap.get("type");
//                         String idValue = identifierMap.get("identifier");
//                         if ("ISBN_13".equals(type)) {
//                             isbn13 = idValue;
//                         } else if ("ISBN_10".equals(type)) {
//                             isbn10 = idValue;
//                         }
//                     }
//                 }
//                 // Prioriza ISBN_13, mas usa ISBN_10 se o primeiro não estiver disponível
//                 dto.setIsbn(isbn13 != null ? isbn13 : isbn10);
//             }

//             livros.add(dto);
//         }
//         return livros;
//     }

//     /**
//      * Busca livros a partir de uma URL de consulta já montada.
//      *
//      * @param url URL de consulta na API do Google Books.
//      * @return Lista de LivroDTO encontrados.
//      * @throws GoogleBooksApiException em caso de erro na comunicação com a API do Google Books.
//      */
//     private List<LivroDTO> buscarLivros(String url){
//         Map<String, Object> response = fazerRequisicaoAPIGoogle(url);

//         List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

//         List<LivroDTO> livros = converterParaLivros(items);
        
//         return livros;

//         //return converterParaLivros((List<Map<String, Object>>) fazerRequisicaoAPIGoogle(url).get("items"));
//     }

//     /**
//      * Monta a URL de consulta para a API do Google Books.
//      *
//      * @param tipoConsulta Tipo de consulta (ex: intitle, inauthor, subject, isbn).
//      * @param valorConsulta Valor a ser pesquisado.
//      * @param maxResults Quantidade máxima de resultados (opcional).
//      * @return String com a URL montada.
//      */
//     private String construirUrl(String tipoConsulta, String valorConsulta, Integer maxResults) {
//         UriComponentsBuilder builder = UriComponentsBuilder
//             .fromPath("/volumes")
//             .queryParam("q", tipoConsulta + ":" + valorConsulta);

//         if (maxResults != null) {
//             builder.queryParam("maxResults", maxResults);
//         }

//         return builder.build().toString();
//     }
// }

