import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../interactor/blocs/events/price_search_event.dart';
import '../interactor/blocs/price_search_bloc.dart';
import '../interactor/blocs/states/price_search_state.dart';

class PriceSearchPage extends StatelessWidget {
  PriceSearchPage({super.key});

  final _searchPriceBloc = Modular.get<PriceSearchBloc>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Pesquisa de preço"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            BlocBuilder<PriceSearchBloc, PriceSearchState>(
                bloc: _searchPriceBloc,
                builder: (context, state) {
                  if (state is PriceSearchInitial) {
                    return const Text(
                        "Aperte o botão para pesquisar o preço do livro");
                  } else if (state is PriceSearchLoading) {
                    return const CircularProgressIndicator();
                  } else if (state is PriceSearchResult) {
                    if (state.result.isNotEmpty) {
                      return Expanded(
                        child: ListView.builder(
                          itemCount: state.result.length,
                          itemBuilder: (context, index) {
                            return ListTile(
                              title: Text(state.result[index]['title']),
                              subtitle:
                                  Text(state.result[index]['price'] ?? ''),
                            );
                          },
                        ),
                      );
                    }
                    return Text("Not found");
                  } else {
                    return const Text("Erro ao pesquisar o preço");
                  }
                }),
            const SizedBox(
              height: 20,
            ),
            ElevatedButton(
              onPressed: () {
                _searchPriceBloc.add(
                  SendPromptEvent(
                    "Encontre 5 ofertas do livro O Hobbit, do autor Tolkien'",
                  ),
                );
              },
              child: const Text("Pesquisar"),
            ),
          ],
        ),
      ),
    );
  }
}
