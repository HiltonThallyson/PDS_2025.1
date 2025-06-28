import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import '../../../domain/infra/repositories/image_gen_repository.dart';

import '../../../domain/infra/services/api/image_gen_api_client.dart';

class ImageGenRepositoryImpl implements ImageGenRepository {
  final ImageGenApiClient _apiClient;
  ImageGenRepositoryImpl(this._apiClient);

  @override
  Future<Uint8List> generateImage(String prompt) {
    return _apiClient.generateImage(prompt);
  }
}
