abstract class BookDetailsPageEvent {}

class PriceSearchEvent extends BookDetailsPageEvent {
  final String searchText;
  PriceSearchEvent(this.searchText);
}
