abstract interface class AuthRepositoryInterface {
  Future<bool> authenticate(Map<String, String> credentials);

  Future<bool> register(Map<String, String> credentials);

  Future<bool> logout();
}
