// package br.imd.mybookplace.controllers;

// import br.imd.mybookplace.DTOS.LivroDTO;
// import br.imd.mybookplace.services.GoogleBookService;

// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.RequestHeader;

// import java.util.List;

// /**
//  * Controller responsável por expor endpoints de busca de livros utilizando a API do Google Books.
//  */
// @RestController
// @RequestMapping("/api/livros")
// public class LivroController {
//     public final GoogleBookService googleService;

//     public LivroController(GoogleBookService googleService) {
//         this.googleService = googleService;
//     }

//     /**
//      * Busca livros pelo título.
//      *
//      * @param titulo Título do livro a ser pesquisado.
//      * @return Lista de livros encontrados.
//      */
//     @GetMapping("/titulo")

//     public List<LivroDTO> buscarLivroPorTitulo(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String titulo){

//         System.out.println(titulo);
//         return googleService.buscarLivrosPorTitulo(titulo);
//     }

//     /**
//      * Busca livros pelo nome do autor.
//      *
//      * @param autor Nome do autor a ser pesquisado.
//      * @return Lista de livros encontrados.
//      */
//     @GetMapping ("/autor")
//     public List<LivroDTO> buscarLivroPorAutor(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String autor){
//         System.out.println(autor);
//         return googleService.buscarLivrosPorAutor(autor);
//     }

//     /**
//      * Busca livros de várias categorias, limitando a quantidade por categoria.
//      *
//      * @param qtdPorCategoria Quantidade máxima de livros por categoria (padrão: 10).
//      * @return Lista de livros encontrados em todas as categorias.
//      */
//     @GetMapping("/quantidade")
//     public List<LivroDTO> buscarLivrosPorQuantidade(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name="qtdPorCategoria", required = false, defaultValue = "10") int qtdPorCategoria){
//         System.out.println(qtdPorCategoria);
//         return googleService.buscarLivrosPorQuantidade(qtdPorCategoria);
//     }

//     /**
//      * Busca livros por uma categoria específica.
//      *
//      * @param categoria Categoria a ser pesquisada.
//      * @return Lista de livros encontrados na categoria.
//      */
//     @GetMapping("/categoria")
//     public List<LivroDTO> buscarLivrosPorCategoria(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String categoria){
//         System.out.println(categoria);
//         return googleService.buscarLivrosPorCategoria(categoria);
//     }

//     /**
//      * Busca livros pelo ISBN informado.
//      *
//      * @param isbn ISBN do livro a ser pesquisado.
//      * @return Lista de livros encontrados com o ISBN informado.
//      */
//     @GetMapping("/isbn")
//     public List<LivroDTO> buscarLivrosPorISBN(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String isbn){
//         System.out.println(isbn);
//         return googleService.buscarLivrosPorISBN(isbn);
//     }
// }