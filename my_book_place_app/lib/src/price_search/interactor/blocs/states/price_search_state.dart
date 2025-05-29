abstract class PriceSearchState {}

class PriceSearchInitial extends PriceSearchState {}

class PriceSearchLoading extends PriceSearchState {}

class PriceSearchResult extends PriceSearchState {
  final List<dynamic> result;

  PriceSearchResult(this.result);
}

class PriceSearchError extends PriceSearchState {
  final String message;

  PriceSearchError(this.message);
}
