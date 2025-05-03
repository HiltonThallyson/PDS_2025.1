import 'package:flutter/material.dart';

import '../../../../themes/app_textstyles.dart';
import '../../interactor/bloc/auth_bloc.dart';
import '../../interactor/bloc/event/auth_event.dart';

class LoginForm extends StatelessWidget {
  final AuthBloc _authBloc;
  const LoginForm({
    required authBloc,
    super.key,
  }) : _authBloc = authBloc;

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: SizedBox(
        height: MediaQuery.of(context).size.height,
        child: Center(
          child: Card(
            elevation: 10,
            color: Colors.white,
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
            child: Container(
              padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 20),
              height: 400,
              width: MediaQuery.of(context).size.width * 0.35 > 400 ? 500 : 300,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Expanded(
                    child: Form(
                      child: SingleChildScrollView(
                        child: Column(
                          children: [
                            TextFormField(
                              decoration: InputDecoration(
                                  label: const Text(
                                    "Username",
                                  ),
                                  floatingLabelStyle: const TextStyle(
                                      color: Colors.indigo, fontSize: 20),
                                  hintText: "Enter your username",
                                  focusedBorder: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(5),
                                      borderSide: const BorderSide(
                                          color: Colors.indigo, width: 2)),
                                  border: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(5))),
                            ),
                            SizedBox(
                                height:
                                    MediaQuery.of(context).size.height * 0.04),
                            TextFormField(
                              decoration: InputDecoration(
                                  label: const Text("Password"),
                                  hintText: "Enter your password",
                                  floatingLabelStyle: const TextStyle(
                                      color: Colors.indigo, fontSize: 20),
                                  focusedBorder: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(5),
                                      borderSide: const BorderSide(
                                          color: Colors.indigo, width: 2)),
                                  border: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(5))),
                            ),
                            const SizedBox(
                              height: 5,
                            ),
                            Align(
                              alignment: Alignment.centerRight,
                              child: TextButton(
                                onPressed: () {},
                                child: const Text(
                                  "Forgot password?",
                                  style: TextStyle(color: Colors.indigo),
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(
                    height: 5,
                  ),
                  Column(
                    mainAxisAlignment: MainAxisAlignment.end,
                    children: [
                      ElevatedButton(
                        style: ElevatedButton.styleFrom(
                            minimumSize: const Size(100, 50),
                            backgroundColor: Colors.pink,
                            shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(15))),
                        onPressed: () {},
                        child: const Text(
                          "Login",
                          style: AppTextStyles.loginButtonText,
                        ),
                      ),
                      const SizedBox(
                        height: 5,
                      ),
                      TextButton(
                        onPressed: () => _authBloc.add(SwitchToSignUpEvent()),
                        child: const Text(
                          "Create an account",
                          style: TextStyle(color: Colors.indigo),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
