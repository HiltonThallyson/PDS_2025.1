import 'dart:convert';
import 'dart:io';

import 'package:flutter_modular/flutter_modular.dart';
import 'package:http/http.dart' as http;

import '../../../domain/infra/repositories/googlebook_repository_interface.dart';
import '../../../domain/infra/services/api/googlebook_api_client.dart';
import '../../../domain/models/game.dart';

class FreeGamesRepositoryImpl implements FreeGamesRepositoryInterface {
  final FreeGamesApiClient _freegamesApiClient;
  FreeGamesRepositoryImpl(this._freegamesApiClient);

  @override
  Future<List<Game>> getGameListByQty(int qty) async {
    return await _freegamesApiClient.getGameListByQty(qty);
  }
}
