import 'dart:convert';
import 'dart:io';

import 'package:flutter_modular/flutter_modular.dart';
import 'package:http/http.dart' as http;

import '../../../../../auth/domain/entities/user.dart';
import '../../../../domain/infra/services/api/googlebook_api_client.dart';
import '../../../../domain/models/game.dart';

class FreeGamesApiClientImpl implements FreeGamesApiClient {
  final _apiUrl = "http://localhost:8090/api/produtos";
  final _gameByQuantity = "/quantidade";

  final _user = Modular.get<User>();

  @override
  Future<List<Game>> getGameListByQty(int qty) async {
    final uri = Uri.parse("$_apiUrl$_gameByQuantity?qtdPorCategoria=$qty");
    final response = await http.get(uri, headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      "Authorization": "Bearer ${_user.token}"
    });
    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      List<Game> games = [];
      for (var game in data) {
        final title = game["title"] ?? "";
        final subtitle = game["subtitle"] ?? "";
        final developersRaw = game["developers"] ?? [];
        final publisher = game["editora"] ?? "";
        final description = game["description"] ?? "";
        var thumbnail = game["thumbnail"] ?? "";

        final categoriesRaw = game["categories"] ?? [];

        final developers =
            List<String>.from(developersRaw.map((a) => a.toString()));
        final categories =
            List<String>.from(categoriesRaw.map((c) => c.toString()));

        final newGame = Game(
            title: title,
            developers: developers,
            description: description,
            publisher: publisher,
            thumbnail: thumbnail,
            subtitle: subtitle,
            categories: categories);
        games.add(newGame);
      }
      return games;
    } else {
      throw const HttpException("Falha ao carregar livros");
    }
  }
}
