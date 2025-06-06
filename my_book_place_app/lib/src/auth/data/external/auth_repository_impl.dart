import 'dart:convert';

import 'package:flutter_modular/flutter_modular.dart';
import 'package:http/http.dart' as http;

import '../../../../core/entities/user.dart';
import '../../interactor/infra/auth_repository_interface.dart';

class AuthRepositoryImpl implements AuthRepositoryInterface {
  final _baseAuthUrl = "http://localhost:8090/api/auth";
  final _loginEnpoint = "/login";
  final _registerEnpoint = "/register";
  final _logoutEnpoint = "/logout";

  final _user = Modular.get<User>();

  @override
  Future<bool> authenticate(Map<String, String> credentials) async {
    final username = credentials["username"];
    final password = credentials["password"];
    final response = await http.post(Uri.parse('$_baseAuthUrl$_loginEnpoint'),
        headers: {
          "Accept": "application/json",
          "Content-Type": "application/json",
          "Authorization": "Bearer ${_user.token}"
        },
        body: jsonEncode(
          {
            "username": username,
            "password": password,
          },
        ));

    if (response.statusCode == 200) {
      final responseBody = jsonDecode(response.body);
      _user.fromJson(responseBody);
      return true;
    } else {
      return false;
    }
  }

  @override
  Future<bool> logout() {
    // TODO: implement logout
    throw UnimplementedError();
  }

  @override
  Future<bool> register(Map<String, String> credentials) async {
    // TODO: implement register
    throw UnimplementedError();
  }
}
