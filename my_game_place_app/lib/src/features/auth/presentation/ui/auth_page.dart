import 'package:fluter_app/src/features/auth/presentation/ui/widgets/signup_form.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../../../../general_widgets/mygameplace_appbar.dart';
import '../blocs/auth_bloc.dart';
import '../blocs/auth_state.dart';
import 'widgets/login_form.dart';

class AuthPage extends StatelessWidget with MyGamePlaceAppBarMixin {
  AuthPage({super.key});

  final _authBloc = Modular.get<AuthBloc>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.indigo,
      extendBody: true,
      appBar: buildAppBar(context),
      body: Stack(
        children: [
          Image.asset("assets/images/mygameplace_background.png",
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
