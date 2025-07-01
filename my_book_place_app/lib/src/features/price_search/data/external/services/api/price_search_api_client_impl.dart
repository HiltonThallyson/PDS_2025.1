import 'dart:convert';
import 'dart:io';

import 'package:fluter_app/src/features/book-details/domain/models/offer.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:http/http.dart' as http;

import '../../../../../auth/domain/entities/user.dart';
import '../../../../domain/infra/services/api/price_search_api_client.dart';

class PriceSearchApiClientImpl implements PriceSearchApiClient {
  final _apiUrl = "http://localhost:8090/api/livros";
  final _searchPriceEndpoint = "/llm/search_price";

  final _user = Modular.get<User>();

  @override
  Future<List<Offer>> getPriceWithAgent(String prompt) async {
    final uri = Uri.parse("$_apiUrl$_searchPriceEndpoint");
    final response = await http.post(
        headers: {
          "Accept": "application/json",
          "Content-Type": "application/json",
          "Authorization": "Bearer ${_user.token}"
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
          answer['price'] != null
              ? answer['price'].toString()
              : "Verifique no site",
          answer['link'],
          answer['imageUrl'] ?? "",
        ));
      }

      return offers;
    } else {
      throw const HttpException("Falha na consulta de preços");
    }
  }
}
