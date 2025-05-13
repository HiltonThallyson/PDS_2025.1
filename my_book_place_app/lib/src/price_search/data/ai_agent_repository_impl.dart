import 'dart:convert';

import 'package:http/http.dart' as http;

import '../interactor/infra/ai_agent_repository.dart';

class AiAgentRepositoryImpl implements AiAgentRepository {
  final _apiUrl = "http://127.0.0.1:8000/api";
  final _searchPriceEndpoint = "/search-price";

  @override
  Future<List<dynamic>> getPriceWithAgent(String prompt) async {
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

      return jsonDecode(data['answer']);
    } else {
      throw Exception("Falha na consulta de preços");
    }
  }
}
