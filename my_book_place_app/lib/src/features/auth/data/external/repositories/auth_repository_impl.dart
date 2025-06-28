import 'dart:convert';

import 'package:flutter_modular/flutter_modular.dart';
import 'package:http/http.dart' as http;

import '../../../domain/infra/repositories/auth_repository_interface.dart';
import '../../../domain/infra/services/api/auth_api_client.dart';

class AuthRepositoryImpl implements AuthRepositoryInterface {
  final AuthApiClient _authApiClient;

  AuthRepositoryImpl(this._authApiClient);

  @override
  Future<bool> authenticate(Map<String, String> credentials) async {
    return await _authApiClient.authenticate(credentials);
  }

  @override
  void logout() {
    return _authApiClient.logout();
  }

  @override
  Future<bool> register(Map<String, String> credentials) async {
    return await _authApiClient.register(credentials);
  }
}
