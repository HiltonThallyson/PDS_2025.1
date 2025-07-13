import 'dart:convert';

import 'package:flutter_modular/flutter_modular.dart';
import 'package:http/http.dart' as http;

import '../../../../domain/entities/user.dart';
import '../../../../domain/infra/services/api/auth_api_client.dart';

class AuthApiClientImpl implements AuthApiClient {
  final _baseAuthUrl = "http://localhost:8090/api/auth";
  final _loginEnpoint = "/login";
  final _registerEnpoint = "/register";

  final _user = Modular.get<User>();
  @override
  Future<bool> authenticate(Map<String, String> credentials) async {
    final username = credentials["username"];
    final password = credentials["password"];
    final response = await http.post(Uri.parse('$_baseAuthUrl$_loginEnpoint'),
        headers: {
          "Accept": "application/json",
          "Content-Type": "application/json",
        },
        body: jsonEncode(
          {
            "username": username,
            "password": password,
          },
        ));

    if (response.statusCode == 200) {
      final responseBody = jsonDecode(response.body);
      _user.token = responseBody["token"];
      return true;
    } else {
      return false;
    }
  }

  @override
  void logout() {
    _user.clear();
  }

  @override
  Future<bool> register(Map<String, String> credentials) async {
    final username = credentials["username"];
    final password = credentials["password"];
    final email = credentials["email"];
    final nickname = credentials["nickname"];
    final response =
        await http.post(Uri.parse('$_baseAuthUrl$_registerEnpoint'),
            headers: {
              "Accept": "application/json",
              "Content-Type": "application/json",
            },
            body: jsonEncode(
              {
                "username": username,
                "password": password,
                "email": email,
                "nickname": nickname,
              },
            ));

    if (response.statusCode == 201) {
      return true;
    } else {
      return false;
    }
  }
}
