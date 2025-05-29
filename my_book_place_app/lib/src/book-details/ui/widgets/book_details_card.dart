import 'package:fluter_app/src/book-details/interactor/blocs/book_details_page_bloc.dart';
import 'package:fluter_app/src/book-details/interactor/blocs/events/book_details_page_event.dart';
import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../../../core/themes/app_textstyles.dart';

class BookDetailsCard extends StatefulWidget {
  final Map<String, dynamic> _book;
  final BookDetailsPageBloc _bookDetailsPageBloc;

  const BookDetailsCard(
      {required Map<String, dynamic> book,
      required BookDetailsPageBloc bloc,
      super.key})
      : _book = book,
        _bookDetailsPageBloc = bloc;

  @override
  State<BookDetailsCard> createState() => _BookDetailsCardState();
}

class _BookDetailsCardState extends State<BookDetailsCard> {
  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 5,
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Row(
              children: [
                Container(
                  width: 100,
                  height: 150,
                  decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(10),
                      color: Colors.indigo),
                ),
                const SizedBox(
                  width: 10,
                ),
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      widget._book['title'],
                      style: AppTextStyles.bookTitle,
                    ),
                    Text(
                      widget._book['subtitle'],
                      style: AppTextStyles.bookSubtitle,
                    ),
                  ],
                )
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Text(widget._book['description']),
          ),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Align(
              alignment: Alignment.centerRight,
              child: ElevatedButton.icon(
                  style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.indigo,
                      foregroundColor: Colors.white,
                      shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(10))),
                  icon: const Icon(
                    Icons.search_rounded,
                    color: Colors.white,
                  ),
                  onPressed: () {
                    widget._bookDetailsPageBloc.add(
                      PriceSearchEvent(
                          "Busque os preços para o livro '${widget._book['title']}' dos autores ${widget._book['authors'].join(', ')}"),
                    );
                  },
                  label: const Text('Pesquisar ofertas')),
            ),
          )
        ],
      ),
    );
  }
}
