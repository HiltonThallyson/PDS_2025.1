import '../../../book-details/models/offer.dart';

abstract interface class AiAgentRepository {
  Future<List<Offer>> getPriceWithAgent(String prompt);
}
