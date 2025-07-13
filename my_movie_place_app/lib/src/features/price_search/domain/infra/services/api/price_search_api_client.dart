import '../../../../../movie-details/domain/models/offer.dart';

abstract interface class PriceSearchApiClient {
  Future<List<Offer>> getPriceWithAgent(String prompt);
}
