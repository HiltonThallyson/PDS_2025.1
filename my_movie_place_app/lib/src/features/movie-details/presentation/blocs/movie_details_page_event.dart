abstract class MovieDetailsPageEvent {}

class PriceSearchEvent extends MovieDetailsPageEvent {
  final String searchText;
  PriceSearchEvent(this.searchText);
}
