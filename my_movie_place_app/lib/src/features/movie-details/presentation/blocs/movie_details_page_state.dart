import '../../domain/models/offer.dart';

abstract class MovieDetailsPageState {}

class MovieDetailsPageInitial extends MovieDetailsPageState {}

class MovieDetailsPageLoading extends MovieDetailsPageState {}

class MovieDetailsPageLoadedOffers extends MovieDetailsPageState {
  final List<Offer> offers;
  MovieDetailsPageLoadedOffers(this.offers);
}

class MovieDetailsPageError extends MovieDetailsPageState {
  final String message;
  MovieDetailsPageError(this.message);
}
