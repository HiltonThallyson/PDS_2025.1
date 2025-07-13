import '../../models/movie.dart';

abstract class TMDBRepositoryInterface {
  Future<List<Movie>> getMovieListByQty(int qty);
  // Future<List<Game>> getGameListByAuthor(String author);
  // Future<List<Game>> getGameListByTitle(String title);
}
