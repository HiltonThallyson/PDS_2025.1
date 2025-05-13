import 'dart:convert';

import 'package:http/http.dart' as http;

import '../../interactor/infra/repositories/googlebook_repository_interface.dart';
import '../../models/book.dart';

class GoogleBookRepositoryImpl implements GoogleBookRepositoryInterface {
  final _apiUrl = "http://localhost:8090/api/livros";
  final _bookByQtyEndpoint = "/quantidade";
  final _bookByAuthorEndpoint = "/autor";
  final _bookByTitleEndpoint = "/titulo";

  @override
  Future<List<Book>> getBookListByQty(int qty) async {
    final uri =
        Uri.parse("$_apiUrl$_bookByQtyEndpoint").replace(queryParameters: {
      "qtdPorCategoria": qty.toString(),
    });

    final response = await http.get(uri, headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
    });
    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      List<Book> books = [];
      for (var book in data) {
        final title = book["title"] ?? "";
        final subtitle = book["subtitle"] ?? "";
        final authorsRaw = book["authors"] ?? [];
        final publisher = book["editora"] ?? "";
        final description = book["description"] ?? "";
        var thumbnail = book["thumbnail"].toString();
        thumbnail = thumbnail.replaceFirst("http://", "https://");

        final categoriesRaw = book["categories"] ?? [];

        final authors = List<String>.from(authorsRaw.map((a) => a.toString()));
        final categories =
            List<String>.from(categoriesRaw.map((c) => c.toString()));

        final newBook = Book(
            title: title,
            authors: authors,
            description: description,
            publisher: publisher,
            thumbnail: thumbnail,
            subtitle: subtitle,
            categories: categories);
        books.add(newBook);
      }
      return books;
    } else {
      throw Exception("Falha ao carregar livros");
    }
  }

  @override
  Future<List<Book>> getBookListByAuthor(String author) {
    // TODO: implement getBookListByAuthor
    throw UnimplementedError();
  }

  @override
  Future<List<Book>> getBookListByTitle(String title) {
    // TODO: implement getBookListByTitle
    throw UnimplementedError();
  }
}
