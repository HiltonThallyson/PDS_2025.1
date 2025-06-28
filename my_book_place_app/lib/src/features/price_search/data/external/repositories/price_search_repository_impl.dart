import 'dart:convert';
import 'dart:io';

import 'package:flutter_modular/flutter_modular.dart';
import 'package:http/http.dart' as http;

import '../../../../book-details/domain/models/offer.dart';
import '../../../domain/infra/repositories/price_search_repository.dart';
import '../../../domain/infra/services/api/price_search_api_client.dart';

class PriceSearchRepositoryImpl implements PriceSearchRepository {
  final PriceSearchApiClient _apiClient;
  PriceSearchRepositoryImpl(this._apiClient);

  @override
  Future<List<Offer>> getPriceWithAgent(String prompt) {
    return _apiClient.getPriceWithAgent(prompt);
  }
}
