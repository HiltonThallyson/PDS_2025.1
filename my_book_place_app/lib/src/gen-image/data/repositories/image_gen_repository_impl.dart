import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import 'package:flutter_modular/flutter_modular.dart';

import '../../../../core/entities/user.dart';
import '../../infra/image_gen_repository.dart';
import 'package:http/http.dart' as http;

class ImageGenRepositoryImpl implements ImageGenRepository {
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
        body: json.encode({"prompt": prompt}));
    print("Content-Type: ${response.headers['content-type']}");
    if (response.statusCode == 200) {
      print(
          'Resposta recebida do servidor: ${response.body} name: ImageGenRepo');
      final data = response.bodyBytes;
      print(data);
      return data;
    } else {
      throw HttpException('Failed to generate image: ${response.reasonPhrase}');
    }
  }
}
