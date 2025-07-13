import 'dart:convert';
import 'dart:io';

import 'package:flutter_modular/flutter_modular.dart';
import 'package:http/http.dart' as http;

import '../../../../../auth/domain/entities/user.dart';
import '../../../../domain/infra/services/api/tmdb_api_client.dart';
import '../../../../domain/models/movie.dart';

class TMDBApiClient implements TMDBApiService {
  final _apiUrl = "http://localhost:8090/api/produtos";
  final _gameByQuantity = "/quantidade";

  final _user = Modular.get<User>();

  @override
  Future<List<Movie>> getMovieListByQty(int qty) async {
    final uri = Uri.parse("$_apiUrl$_gameByQuantity?qtdPorCategoria=$qty");
    final response = await http.get(uri, headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      "Authorization": "Bearer ${_user.token}"
    });
    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      List<Movie> movies = [];
      for (var movie in data) {
        final title = movie["title"] ?? "";
        final subtitle = movie["subtitle"] ?? "";
        final developersRaw = movie["developers"] ?? [];
        final publisher = movie["editora"] ?? "";
        final description = movie["description"] ?? "";
        var thumbnail = movie["thumbnail"] ?? "";

        final categoriesRaw = movie["categories"] ?? [];

        final developers =
            List<String>.from(developersRaw.map((a) => a.toString()));
        final categories =
            List<String>.from(categoriesRaw.map((c) => c.toString()));

        final newMovie = Movie(
            title: title,
            developers: developers,
            description: description,
            publisher: publisher,
            thumbnail: thumbnail,
            subtitle: subtitle,
            categories: categories);
        movies.add(newMovie);
      }
      return movies;
    } else {
      throw const HttpException("Falha ao carregar livros");
    }
  }
}
