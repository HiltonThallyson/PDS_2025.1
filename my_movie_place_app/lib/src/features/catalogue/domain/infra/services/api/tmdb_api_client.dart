import '../../../models/movie.dart';

abstract interface class TMDBApiService {
  Future<List<Movie>> getMovieListByQty(int qty);
  // Future<List<Game>> getBookListByAuthor(String author);
  // Future<List<Game>> getBookListByTitle(String title);
}
