import '../../domain/models/movie.dart';

abstract class CataloguePageState {}

class CataloguePageLoadingState extends CataloguePageState {}

class CataloguePageLoadedState extends CataloguePageState {
  final List<Movie> movieList;
  CataloguePageLoadedState(this.movieList);
}

class CataloguePageErrorState extends CataloguePageState {
  final String errorMessage;
  CataloguePageErrorState(this.errorMessage);
}
