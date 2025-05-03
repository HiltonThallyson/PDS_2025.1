import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../../../core/themes/app_textstyles.dart';
import '../../interactor/bloc/auth_bloc.dart';
import '../../interactor/bloc/event/auth_event.dart';

class LoginForm extends StatelessWidget {
  final AuthBloc _authBloc;
  LoginForm({
    required authBloc,
    super.key,
  }) : _authBloc = authBloc;

  final _formKey = GlobalKey<FormState>();
  final _usernameController = TextEditingController();
  final _passwordController = TextEditingController();
  final _passwordFocusNode = FocusNode();
  final _usernameFocusNode = FocusNode();
  final _passwordVisible = ValueNotifier<bool>(false);

  void _validateAndSubmit(BuildContext context) async {
    if (_formKey.currentState!.validate()) {
      final credentials = {
        "username": _usernameController.text,
        "password": _passwordController.text,
      };
      try {
        await _authBloc.login(credentials).then(
          (success) {
            if (success) {
              Modular.to.navigate("/home/");
            } else {
              if (!context.mounted) {
                return;
              }
              ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
                content: Text("Credenciais inválidas!"),
                duration: Duration(seconds: 2),
              ));
            }
          },
        );
      } on HttpException catch (_) {
        if (!context.mounted) {
          return;
        }
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content:
                Text("Um erro ocorreu ao tentar autenticar! Tente novamente."),
            duration: const Duration(seconds: 2),
          ),
        );
      } catch (e) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content:
                Text("Um erro ocorreu ao tentar autenticar! Tente novamente."),
            // Text(e.toString()),
            duration: const Duration(seconds: 2),
          ),
        );
      }
    }
  }

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
                      key: _formKey,
                      child: SingleChildScrollView(
                        child: Column(
                          children: [
                            TextFormField(
                              controller: _usernameController,
                              focusNode: _usernameFocusNode,
                              onFieldSubmitted: (value) {
                                _usernameFocusNode.unfocus();
                              },
                              validator: (value) {
                                if (value == null || value.isEmpty) {
                                  return "Campo obrigatório";
                                }
                                return null;
                              },
                              decoration: InputDecoration(
                                  isDense: true,
                                  icon: const Icon(Icons.person),
                                  label: const Text(
                                    "Usuário",
                                  ),
                                  floatingLabelStyle: const TextStyle(
                                      color: Colors.indigo, fontSize: 20),
                                  hintText: "Insira seu nome de usuário",
                                  focusedBorder: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(5),
                                      borderSide: const BorderSide(
                                          color: Colors.indigo, width: 2)),
                                  border: OutlineInputBorder(
                                      borderRadius: BorderRadius.circular(5))),
                            ),
                            SizedBox(
                                height:
                                    MediaQuery.of(context).size.height * 0.03),
                            TextFormField(
                              controller: _passwordController,
                              focusNode: _passwordFocusNode,
                              obscureText: !_passwordVisible.value,
                              onFieldSubmitted: (value) {
                                _passwordFocusNode.unfocus();
                              },
                              validator: (value) {
                                if (value == null || value.isEmpty) {
                                  return "Campo obrigatório";
                                }
                                // if (value.length < 8) {
                                //   return "A senha deve ter no mínimo 8 caracteres";
                                // }
                                return null;
                              },
                              decoration: InputDecoration(
                                  isDense: true,
                                  icon: const Icon(Icons.lock),
                                  label: const Text("Senha"),
                                  hintText: "Insira sua senha",
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
                                  "Esqueceu sua senha? Clique aqui",
                                  style: AppTextStyles.textButtonsStyle,
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
                        onPressed: () => _validateAndSubmit(context),
                        child: const Text(
                          "Entrar",
                          style: AppTextStyles.loginButtonText,
                        ),
                      ),
                      const SizedBox(
                        height: 5,
                      ),
                      TextButton(
                        onPressed: () => _authBloc.add(SwitchToSignUpEvent()),
                        child: const Text(
                          "Crie sua conta aqui!",
                          style: AppTextStyles.textButtonsStyle,
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
