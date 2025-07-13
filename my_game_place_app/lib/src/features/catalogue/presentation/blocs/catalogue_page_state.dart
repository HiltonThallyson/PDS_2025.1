import '../../domain/models/game.dart';

abstract class CataloguePageState {}

class CataloguePageLoadingState extends CataloguePageState {}

class CataloguePageLoadedState extends CataloguePageState {
  final List<Game> gameList;
  CataloguePageLoadedState(this.gameList);
}

class CataloguePageErrorState extends CataloguePageState {
  final String errorMessage;
  CataloguePageErrorState(this.errorMessage);
}
