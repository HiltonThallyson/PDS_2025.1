abstract class GameDetailsPageEvent {}

class PriceSearchEvent extends GameDetailsPageEvent {
  final String searchText;
  PriceSearchEvent(this.searchText);
}
