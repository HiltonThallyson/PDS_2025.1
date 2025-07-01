import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import 'package:flutter_modular/flutter_modular.dart';
import 'package:http/http.dart' as http;

import '../../../../../auth/domain/entities/user.dart';
import '../../../../domain/infra/services/api/image_gen_api_client.dart';

class ImageGenApiClientImpl implements ImageGenApiClient {
  final _baseUrl = "http://localhost:8090/api/llm";
  final _imageGenEndpoint = "/generate_image_by_text";

  final _user = Modular.get<User>();

  @override
  Future<Uint8List> generateImage(String prompt) async {
    final uri = Uri.parse('$_baseUrl$_imageGenEndpoint');
    final response = await http.post(uri,
        headers: {
          "Content-Type": "application/json",
          // "Accept": "*/*",
          "Authorization": "Bearer ${_user.token}"
        },
        body: jsonEncode({"prompt": prompt}));
    if (response.statusCode == 200) {
      final data = response.bodyBytes;
      return data;
    } else {
      throw const HttpException('Falha ao gerar a imagem.');
    }
  }
}
