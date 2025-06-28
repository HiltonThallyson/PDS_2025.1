import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../../../../general_widgets/mybookplace_appbar.dart';
import '../blocs/events/gen_image_events.dart';
import '../blocs/gen_image_bloc.dart';
import '../blocs/states/gen_image_states.dart';

class GenImagePage extends StatefulWidget {
  const GenImagePage({super.key});

  @override
  State<GenImagePage> createState() => _GenImagePageState();
}

class _GenImagePageState extends State<GenImagePage>
    with MyBookPlaceAppBarMixin {
  String _prompt = "";
  final _genImageBloc = Modular.get<GenImageBloc>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.indigo,
      appBar: buildAppBar(context),
      body: SingleChildScrollView(
        child: Container(
          height: MediaQuery.of(context).size.height * 0.85,
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(10),
          ),
          padding: const EdgeInsets.all(5.0),
          margin: const EdgeInsets.all(10.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Expanded(
                child: Container(
                  padding: const EdgeInsets.all(10),
                  width: double.maxFinite,
                  height: MediaQuery.of(context).size.height * 0.5,
                  child: BlocBuilder<GenImageBloc, GenImageState>(
                      bloc: _genImageBloc,
                      builder: (context, state) {
                        if (state is GenImageInitialState) {
                          return const Center(
                            child: Text(
                              "Digite um prompt para gerar uma imagem",
                              style: TextStyle(fontSize: 20),
                            ),
                          );
                        } else if (state is GenImageLoadingState) {
                          return const Center(
                            child: CircularProgressIndicator(),
                          );
                        } else if (state is GenImageSuccessState) {
                          return Image.memory(
                            state.imageUrl,
                            fit: BoxFit.fitHeight,
                            width: double.maxFinite,
                            height: MediaQuery.of(context).size.height * 0.45,
                          );
                        } else if (state is GenImageErrorState) {
                          return Center(
                            child: Text(
                              state.errorMessage,
                              style: const TextStyle(color: Colors.red),
                            ),
                          );
                        }
                        return Container();
                      }),
                ),
              ),
              const Divider(
                color: Colors.black,
                height: 1,
              ),
              Padding(
                padding: const EdgeInsets.symmetric(vertical: 10.0),
                child: Row(
                  children: [
                    SizedBox(
                      width: MediaQuery.of(context).size.width * 0.8,
                      child: TextField(
                        decoration: InputDecoration(
                          labelText: "Descreva a imagem que deseja gerar",
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(10),
                          ),
                        ),
                        onChanged: (value) {
                          setState(() {
                            _prompt = value;
                          });
                        },
                      ),
                    ),
                    const SizedBox(width: 10),
                    ElevatedButton(
                      onPressed: () {
                        if (_prompt.isEmpty) {
                          ScaffoldMessenger.of(context).showSnackBar(
                            const SnackBar(
                              content: Text("Por favor, insira um prompt."),
                            ),
                          );
                          return;
                        } else {
                          final finalPrompt =
                              "Gere uma imagem de acordo com essa descrição: $_prompt";
                          _genImageBloc.add(GenerateImageEvent(finalPrompt));
                        }

                        ScaffoldMessenger.of(context).showSnackBar(
                          SnackBar(
                            content: Text("Gerando imagem para: $_prompt"),
                          ),
                        );
                      },
                      child: const Text(
                        "Gerar imagem",
                        style: TextStyle(
                            fontSize: 20, fontWeight: FontWeight.bold),
                      ),
                    ),
                  ],
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}
