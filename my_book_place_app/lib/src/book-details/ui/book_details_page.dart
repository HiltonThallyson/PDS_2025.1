import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../general_widgets/mybookplace_appbar.dart';
import '../interactor/blocs/book_details_page_bloc.dart';
import '../interactor/blocs/states/book_details_page_state.dart';
import 'widgets/book_details_card.dart';
import 'widgets/offer_card.dart';

class BookDetailsPage extends StatefulWidget {
  final Map<String, dynamic> _book;
  const BookDetailsPage({required Map<String, dynamic> book, super.key})
      : _book = book;

  @override
  State<BookDetailsPage> createState() => _BookDetailsPageState();
}

class _BookDetailsPageState extends State<BookDetailsPage>
    with MyBookPlaceAppBarMixin {
  final _bookDetailsPageBloc = Modular.get<BookDetailsPageBloc>();
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
                BookDetailsCard(
                  book: widget._book,
                  bloc: _bookDetailsPageBloc,
                ),
                Padding(
                  padding: const EdgeInsets.all(3.0),
                  child: BlocBuilder<BookDetailsPageBloc, BookDetailsPageState>(
                      bloc: _bookDetailsPageBloc,
                      builder: (context, state) {
                        if (state is BookDetailsPageLoading) {
                          return Center(
                            child: SizedBox(
                              height: 300,
                              width: double.maxFinite,
                              child: Card(
                                elevation: 5,
                                shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(10)),
                                child: const SizedBox(
                                  height: 50,
                                  width: 100,
                                  child: LinearProgressIndicator(
                                    color: Colors.indigo,
                                  ),
                                ),
                              ),
                            ),
                          );
                        } else if (state is BookDetailsPageLoadedOffers) {
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
                                        style: TextStyle(color: Colors.black),
                                      ),
                                    ),
                            ),
                          );
                        } else if (state is BookDetailsPageError) {
                          return Center(
                            child: Card(
                              elevation: 5,
                              shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(10)),
                              child: const SizedBox(
                                height: 300,
                                child: Text('Erro ao carregar os preços',
                                    style: TextStyle(color: Colors.black)),
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
