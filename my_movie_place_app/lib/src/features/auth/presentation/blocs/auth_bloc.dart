import 'dart:io';

import 'package:bloc/bloc.dart';

import '../../domain/infra/repositories/auth_repository_interface.dart';
import 'auth_event.dart';
import 'auth_state.dart';

class AuthBloc extends Bloc<AuthEvent, AuthState> {
  final AuthRepositoryInterface _authRepository;

  AuthBloc(AuthRepositoryInterface authRepository)
      : _authRepository = authRepository,
        super(AuthLoginState()) {
    on<SwitchToLoginEvent>((event, emit) {
      emit(AuthLoginState());
    });

    on<SwitchToSignUpEvent>((event, emit) {
      emit(AuthSignUpState());
    });
  }

  Future<bool> login(Map<String, String> credentials) async {
    try {
      final result = await _authRepository.authenticate(credentials);
      return result;
    } on HttpException catch (_) {
      rethrow;
    } catch (e) {
      rethrow;
    }
  }

  Future<bool> signUp(Map<String, String> credentials) async {
    try {
      final result = await _authRepository.register(credentials);
      return result;
    } on HttpException catch (_) {
      rethrow;
    } catch (e) {
      rethrow;
    }
  }

  void logout() {
    // emit(AuthLoggedOut());
  }
}
