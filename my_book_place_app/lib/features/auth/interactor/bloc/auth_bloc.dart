import 'package:bloc/bloc.dart';

import 'event/auth_event.dart';
import 'state/auth_state.dart';

class AuthBloc extends Bloc<AuthEvent, AuthState> {
  AuthBloc() : super(AuthLoginState()) {
    on<SwitchToLoginEvent>((event, emit) {
      emit(AuthLoginState());
    });

    on<SwitchToSignUpEvent>((event, emit) {
      emit(AuthSignUpState());
    });

    on<LoginEvent>((event, emit) {});

    on<SignUpEvent>((event, emit) {});
  }

  void login(String username, String password) {
    // Simulate a login process
    if (username == 'user' && password == 'password') {
    } else {
      // add.AuthErrorState('Invalid credentials'));
    }
  }

  void logout() {
    // emit(AuthLoggedOut());
  }
}
