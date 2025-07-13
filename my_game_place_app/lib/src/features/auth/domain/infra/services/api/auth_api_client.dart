abstract interface class AuthApiClient {
  Future<bool> authenticate(Map<String, String> credentials);
  Future<bool> register(Map<String, String> credentials);
  void logout();
}
