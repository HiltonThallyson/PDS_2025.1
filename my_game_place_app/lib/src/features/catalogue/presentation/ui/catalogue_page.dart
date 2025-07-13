import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../../../../general_widgets/mygameplace_appbar.dart';
import '../blocs/catalogue_page_bloc.dart';
import '../blocs/catalogue_page_event.dart';
import '../blocs/catalogue_page_state.dart';
import 'widgets/game_card.dart';

class CataloguePage extends StatefulWidget {
  const CataloguePage({super.key});

  @override
  State<CataloguePage> createState() => _CataloguePageState();
}

class _CataloguePageState extends State<CataloguePage>
    with MyGamePlaceAppBarMixin {
  final _cataloguePageBloc = Modular.get<CataloguePageBloc>();

  @override
  void initState() {
    super.initState();
    _loadGames();
  }

  void _loadGames() async {
    Future.delayed(const Duration(seconds: 2), () {
      _cataloguePageBloc.add(CataloguePageLoadEvent(30));
    });
  }

  @override
  Widget build(BuildContext context) {
    return SelectionArea(
      child: Scaffold(
        backgroundColor: Colors.indigo,
        appBar: buildAppBar(context),
        body: BlocBuilder<CataloguePageBloc, CataloguePageState>(
          bloc: _cataloguePageBloc,
          builder: (context, state) {
            if (state is CataloguePageLoadingState) {
              return const Center(
                child: CircularProgressIndicator(
                  color: Colors.white,
                ),
              );
            } else if (state is CataloguePageLoadedState) {
              final gameList = state.gameList;
              return SingleChildScrollView(
                child: Padding(
                  padding: const EdgeInsets.all(20),
                  child: Column(
                    children: [
                      const Align(
                        alignment: Alignment.centerLeft,
                        child: Text(
                          "Todos os Jogos",
                          style: TextStyle(color: Colors.white, fontSize: 20),
                        ),
                      ),
                      SizedBox(
                        height: 500,
                        child: GridView.builder(
                          gridDelegate:
                              const SliverGridDelegateWithMaxCrossAxisExtent(
                                  maxCrossAxisExtent: 400),
                          itemBuilder: (context, index) {
                            final game = gameList[index].getGameInfo();
                            return InkWell(
                              onTap: () => Modular.to.pushNamed(
                                "/game-details/",
                                arguments: game,
                              ),
                              child: GameCard(
                                  game: game, size: const Size(200, 600)),
                            );
                          },
                          itemCount: gameList.length,
                        ),
                      ),
                    ],
                  ),
                ),
              );
            } else if (state is CataloguePageErrorState) {
              return Center(
                child: Text(
                  state.errorMessage,
                  style: const TextStyle(color: Colors.white),
                ),
              );
            } else {
              return const Center(
                child: Text("Error"),
              );
            }
          },
        ),
      ),
    );
  }
}
