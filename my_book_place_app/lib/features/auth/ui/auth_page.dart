import 'package:fluter_app/features/auth/ui/widgets/signup_form.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../interactor/bloc/auth_bloc.dart';
import '../interactor/bloc/state/auth_state.dart';
import 'widgets/login_form.dart';

class AuthPage extends StatelessWidget {
  AuthPage({super.key});

  final _authBloc = Modular.get<AuthBloc>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.transparent,
      extendBody: true,
      appBar: AppBar(
        centerTitle: true,
        backgroundColor: Colors.indigo,
        shadowColor: Colors.transparent,
        elevation: 0,
        title: const Text(
          "MyBookPlace",
          style: TextStyle(color: Colors.white, fontSize: 40),
        ),
      ),
      body: Stack(
        children: [
          Image.asset("assets/images/mybookplace_background.png",
              fit: BoxFit.cover,
              height: MediaQuery.of(context).size.height,
              width: MediaQuery.of(context).size.width),
          BlocBuilder<AuthBloc, AuthState>(
            bloc: _authBloc,
            builder: (context, state) {
              if (state is AuthLoginState) {
                return LoginForm(
                  authBloc: _authBloc,
                );
              } else {
                return SignUpForm(
                  authBloc: _authBloc,
                );
              }
            },
          ),
        ],
      ),
    );
  }
}
