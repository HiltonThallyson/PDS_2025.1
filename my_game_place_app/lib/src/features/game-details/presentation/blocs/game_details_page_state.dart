import '../../domain/models/offer.dart';

abstract class GameDetailsPageState {}

class GameDetailsPageInitial extends GameDetailsPageState {}

class GameDetailsPageLoading extends GameDetailsPageState {}

class GameDetailsPageLoadedOffers extends GameDetailsPageState {
  final List<Offer> offers;
  GameDetailsPageLoadedOffers(this.offers);
}

class GameDetailsPageError extends GameDetailsPageState {
  final String message;
  GameDetailsPageError(this.message);
}
