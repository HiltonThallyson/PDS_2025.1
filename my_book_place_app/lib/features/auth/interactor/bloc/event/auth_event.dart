abstract class AuthEvent {}

class SwitchToLoginEvent extends AuthEvent {}

class SwitchToSignUpEvent extends AuthEvent {}

class LoginEvent extends AuthEvent {
  final String username;
  final String password;

  LoginEvent(this.username, this.password);
}

class SignUpEvent extends AuthEvent {
  final String username;
  final String password;

  SignUpEvent(this.username, this.password);
}

class LogoutEvent extends AuthEvent {}

class ErrorEvent extends AuthEvent {
  final String message;

  ErrorEvent(this.message);
}
