import 'dart:convert';

import 'package:http/http.dart' as http;

import '../../book-details/models/offer.dart';
import '../interactor/infra/ai_agent_repository.dart';

class AiAgentRepositoryImpl implements AiAgentRepository {
  final _apiUrl = "http://127.0.0.1:8000/api";
  final _searchPriceEndpoint = "/search-price";

  @override
  Future<List<Offer>> getPriceWithAgent(String prompt) async {
    final uri = Uri.parse("$_apiUrl$_searchPriceEndpoint");
    final response = await http.post(
        headers: {
          "Accept": "application/json",
          "Content-Type": "application/json",
        },
        uri,
        body: jsonEncode({
          "prompt": prompt,
        }));

    if (response.statusCode == 200) {
      final data = jsonDecode(utf8.decode(response.bodyBytes));

      List<Offer> offers = [];
      for (var answer in data) {
        offers.add(Offer(
          answer['title'],
          answer['price'] ?? "Verifique no site",
          answer['link'],
          answer['imageUrl'] ?? "",
        ));
      }

      return offers;
    } else {
      throw Exception("Falha na consulta de preços");
    }
  }
}
