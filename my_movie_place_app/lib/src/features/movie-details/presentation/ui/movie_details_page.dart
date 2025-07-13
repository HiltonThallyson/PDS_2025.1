import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../../../../general_widgets/mymovieplace_appbar.dart';
import '../blocs/movie_details_page_bloc.dart';
import '../blocs/movie_details_page_state.dart';
import 'widgets/game_details_card.dart';
import 'widgets/offer_card.dart';

class MovieDetailsPage extends StatefulWidget {
  final Map<String, dynamic> _movie;
  const MovieDetailsPage({required Map<String, dynamic> movie, super.key})
      : _movie = movie;

  @override
  State<MovieDetailsPage> createState() => _MovieDetailsPageState();
}

class _MovieDetailsPageState extends State<MovieDetailsPage>
    with MyMoviePlaceAppBarMixin {
  final _MovieDetailsPageBloc = Modular.get<MovieDetailsPageBloc>();
  @override
  Widget build(BuildContext context) {
    return SelectionArea(
      child: Scaffold(
        backgroundColor: Colors.indigo,
        appBar: buildAppBar(context),
        body: Padding(
          padding: const EdgeInsets.all(10.0),
          child: SingleChildScrollView(
            child: Wrap(
              children: [
                MovieDetailsCard(
                  movie: widget._movie,
                  bloc: _MovieDetailsPageBloc,
                ),
                Padding(
                  padding: const EdgeInsets.all(3.0),
                  child:
                      BlocBuilder<MovieDetailsPageBloc, MovieDetailsPageState>(
                          bloc: _MovieDetailsPageBloc,
                          builder: (context, state) {
                            if (state is MovieDetailsPageLoading) {
                              return Center(
                                child: SizedBox(
                                  height: 300,
                                  width: double.maxFinite,
                                  child: Card(
                                    elevation: 5,
                                    shape: RoundedRectangleBorder(
                                        borderRadius:
                                            BorderRadius.circular(10)),
                                    child: Container(
                                      height: 50,
                                      width: 100,
                                      padding: const EdgeInsets.symmetric(
                                          horizontal: 50, vertical: 100),
                                      child: LinearProgressIndicator(
                                        color: Colors.indigo,
                                      ),
                                    ),
                                  ),
                                ),
                              );
                            } else if (state is MovieDetailsPageLoadedOffers) {
                              final offers = state.offers;
                              return Card(
                                elevation: 5,
                                shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(10)),
                                child: Container(
                                  padding: const EdgeInsets.all(20),
                                  decoration: BoxDecoration(
                                    borderRadius: BorderRadius.circular(10),
                                    color: Colors.white,
                                  ),
                                  height: 500,
                                  width: double.maxFinite,
                                  child: offers.isNotEmpty
                                      ? GridView.builder(
                                          itemCount: offers.length,
                                          gridDelegate:
                                              const SliverGridDelegateWithFixedCrossAxisCount(
                                            crossAxisCount: 3,
                                            mainAxisSpacing: 10,
                                            crossAxisSpacing: 10,
                                            childAspectRatio: 1.2,
                                          ),
                                          itemBuilder: (_, index) {
                                            final offer =
                                                offers[index].getOfferInfo();
                                            return OfferCard(
                                              offer: offer,
                                            );
                                          })
                                      : const Center(
                                          child: Text(
                                            'Nenhuma oferta encontrada',
                                            style: TextStyle(
                                                color: Colors.black,
                                                fontSize: 36),
                                          ),
                                        ),
                                ),
                              );
                            } else if (state is MovieDetailsPageError) {
                              return Card(
                                elevation: 5,
                                shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(10)),
                                child: const SizedBox(
                                  height: 300,
                                  width: double.maxFinite,
                                  child: Center(
                                    child: Text(
                                      'Houve um problema ao carregar as ofertas, tente novamente.',
                                      style: TextStyle(
                                          color: Colors.black, fontSize: 36),
                                    ),
                                  ),
                                ),
                              );
                            }
                            return Container(
                              height: 300,
                              decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(10),
                                color: Colors.white,
                              ),
                            );
                          }),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
