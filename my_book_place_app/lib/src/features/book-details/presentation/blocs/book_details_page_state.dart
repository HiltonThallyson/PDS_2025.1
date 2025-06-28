import '../../domain/models/offer.dart';

abstract class BookDetailsPageState {}

class BookDetailsPageInitial extends BookDetailsPageState {}

class BookDetailsPageLoading extends BookDetailsPageState {}

class BookDetailsPageLoadedOffers extends BookDetailsPageState {
  final List<Offer> offers;
  BookDetailsPageLoadedOffers(this.offers);
}

class BookDetailsPageError extends BookDetailsPageState {
  final String message;
  BookDetailsPageError(this.message);
}
