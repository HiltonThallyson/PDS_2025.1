import '../../../../movie-details/domain/models/offer.dart';

abstract interface class PriceSearchRepository {
  Future<List<Offer>> getPriceWithAgent(String prompt);
}
