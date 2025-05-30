import 'package:flutter/material.dart';

import '../../../../../../core/themes/app_textstyles.dart';
import '../../blocs/auth_bloc.dart';
import '../../blocs/auth_event.dart';

class SignUpForm extends StatelessWidget {
  final AuthBloc _authBloc;
  const SignUpForm({
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
                              keyboardType: TextInputType.text,
                              decoration: InputDecoration(
                                  isDense: true,
                                  icon: const Icon(Icons.person),
                                  label: const Text("Usuário"),
                                  hintText: "Insira seu nome de usuário",
                                  floatingLabelStyle: const TextStyle(
                                      color: Colors.indigo, fontSize: 20),
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
                              keyboardType: TextInputType.text,
                              decoration: InputDecoration(
                                  isDense: true,
                                  icon: const Icon(Icons.lock),
                                  label: const Text("Senha"),
                                  hintText:
                                      "Insira sua senha, no mínimo 8 caracteres",
                                  floatingLabelStyle: const TextStyle(
                                      color: Colors.indigo, fontSize: 20),
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
                              keyboardType: TextInputType.emailAddress,
                              decoration: InputDecoration(
                                  isDense: true,
                                  icon: const Icon(Icons.email),
                                  label: const Text("Email"),
                                  hintText: "Insira seu email",
                                  floatingLabelStyle: const TextStyle(
                                      color: Colors.indigo, fontSize: 20),
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
                              keyboardType: TextInputType.text,
                              decoration: InputDecoration(
                                  isDense: true,
                                  icon: const Icon(Icons.person_pin_sharp),
                                  label: const Text("Apelido"),
                                  hintText: "Insira seu apelido",
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
                          "Criar conta",
                          style: AppTextStyles.loginButtonText,
                        ),
                      ),
                      const SizedBox(
                        height: 5,
                      ),
                      TextButton(
                        onPressed: () => _authBloc.add(SwitchToLoginEvent()),
                        child: const Text(
                          "Já possui uma conta? Clique aqui para entrar!",
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
