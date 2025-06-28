import 'dart:convert';
import 'dart:io';

import 'package:flutter_modular/flutter_modular.dart';
import 'package:http/http.dart' as http;

import '../../../../auth/domain/entities/user.dart';
import '../../../domain/infra/repositories/googlebook_repository_interface.dart';
import '../../../domain/infra/services/api/googlebook_api_client.dart';
import '../../../domain/models/book.dart';

class GoogleBookRepositoryImpl implements GoogleBookRepositoryInterface {
  final GoogleBookApiClient _googleBookApiClient;
  GoogleBookRepositoryImpl(this._googleBookApiClient);

  @override
  Future<List<Book>> getBookListByQty(int qty) async {
    return await _googleBookApiClient.getBookListByQty(qty);
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
