import '../../../domain/infra/repositories/tmdb_repository_interface.dart';
import '../../../domain/infra/services/api/tmdb_api_client.dart';
import '../../../domain/models/movie.dart';

class TMDBRepositoryImpl implements TMDBRepositoryInterface {
  final TMDBApiService _tmdbApiClient;
  TMDBRepositoryImpl(this._tmdbApiClient);

  @override
  Future<List<Movie>> getMovieListByQty(int qty) async {
    return await _tmdbApiClient.getMovieListByQty(qty);
  }
}
