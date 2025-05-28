import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../general_widgets/mybookplace_appbar.dart';
import '../interactor/blocs/catalogue_page_bloc.dart';
import '../interactor/blocs/events/catalogue_page_event.dart';
import '../interactor/blocs/states/catalogue_page_state.dart';
import 'widgets/book_card.dart';

class CataloguePage extends StatefulWidget {
  const CataloguePage({super.key});

  @override
  State<CataloguePage> createState() => _CataloguePageState();
}

class _CataloguePageState extends State<CataloguePage>
    with MyBookPlaceAppBarMixin {
  final _cataloguePageBloc = Modular.get<CataloguePageBloc>();

  @override
  void initState() {
    super.initState();
    _loadBooks();
  }

  void _loadBooks() async {
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
              final bookList = state.bookList;
              return SingleChildScrollView(
                child: Padding(
                  padding: const EdgeInsets.all(20),
                  child: Column(
                    children: [
                      // const Align(
                      //   alignment: Alignment.centerLeft,
                      //   child: Text(
                      //     "Meus favoritos",
                      //     style: TextStyle(color: Colors.white, fontSize: 20),
                      //   ),
                      // ),
                      // SizedBox(
                      //   height: 300,
                      //   width: MediaQuery.of(context).size.width,
                      //   child: ListView.builder(
                      //     itemBuilder: (context, index) {
                      //       return Card(
                      //         color: Colors.white,
                      //         child: Container(
                      //           height: 200,
                      //           width: 150,
                      //           margin: const EdgeInsets.all(10),
                      //           child: const Column(
                      //             mainAxisSize: MainAxisSize.min,
                      //             children: [
                      //               Text("Título do livro"),
                      //               Text("Autor do livro"),
                      //             ],
                      //           ),
                      //         ),
                      //       );
                      //     },
                      //     itemCount: 10,
                      //     shrinkWrap: true,
                      //     scrollDirection: Axis.horizontal,
                      //     // physics: const NeverScrollableScrollPhysics(),
                      //   ),
                      // ),
                      // const SizedBox(
                      //   height: 20,
                      // ),
                      // const Align(
                      //   alignment: Alignment.centerLeft,
                      //   child: Text(
                      //     "Livros mais votados",
                      //     style: TextStyle(color: Colors.white, fontSize: 20),
                      //   ),
                      // ),
                      // SizedBox(
                      //   height: 300,
                      //   width: MediaQuery.of(context).size.width,
                      //   child: ListView.builder(
                      //     itemBuilder: (context, index) {
                      //       return Card(
                      //         color: Colors.white,
                      //         child: Container(
                      //           height: 200,
                      //           width: 150,
                      //           margin: const EdgeInsets.all(10),
                      //           child: const Column(
                      //             mainAxisSize: MainAxisSize.min,
                      //             children: [
                      //               Text("Titulo do livro"),
                      //               Text("Autor do livro"),
                      //             ],
                      //           ),
                      //         ),
                      //       );
                      //     },
                      //     itemCount: bookList.length,
                      //     shrinkWrap: true,
                      //     scrollDirection: Axis.horizontal,
                      //     // physics: const NeverScrollableScrollPhysics(),
                      //   ),
                      // ),
                      // const SizedBox(
                      //   height: 20,
                      // ),
                      const Align(
                        alignment: Alignment.centerLeft,
                        child: Text(
                          "Todos os livros",
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
                            final book = bookList[index].getBookInfo();
                            print("book info: $book");
                            return InkWell(
                              onTap: () => Modular.to.pushNamed(
                                "/book-details/",
                                arguments: book,
                              ),
                              child: BookCard(
                                  book: book, size: const Size(200, 600)),
                            );
                          },
                          itemCount: bookList.length,
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
