abstract interface class AuthRepositoryInterface {
  Future<dynamic> authenticate(Map<String, String> credentials);
  Future<bool> register(Map<String, String> credentials);
  void logout();
}
