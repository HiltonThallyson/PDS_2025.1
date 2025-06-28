<<<<<<< Updated upstream:my_book_place_app/lib/src/auth/interactor/infra/auth_repository_interface.dart
abstract interface class AuthRepositoryInterface {
  Future<bool> authenticate(Map<String, String> credentials);

=======
abstract interface class AuthApiClient {
  Future<dynamic> authenticate(Map<String, String> credentials);
>>>>>>> Stashed changes:my_book_place_app/lib/src/features/auth/domain/infra/services/api/auth_api_client.dart
  Future<bool> register(Map<String, String> credentials);

  Future<bool> logout();
}
