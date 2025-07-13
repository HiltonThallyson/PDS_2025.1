import 'package:fluter_app/src/features/movie-details/presentation/blocs/movie_details_page_bloc.dart';
import 'package:fluter_app/src/features/movie-details/presentation/blocs/movie_details_page_event.dart';
import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../../../../../core/themes/app_textstyles.dart';

class MovieDetailsCard extends StatefulWidget {
  final Map<String, dynamic> _movie;
  final MovieDetailsPageBloc _movieDetailsPageBloc;

  const MovieDetailsCard(
      {required Map<String, dynamic> movie,
      required MovieDetailsPageBloc bloc,
      super.key})
      : _movie = movie,
        _movieDetailsPageBloc = bloc;

  @override
  State<MovieDetailsCard> createState() => _GameDetailsCardState();
}

class _GameDetailsCardState extends State<MovieDetailsCard> {
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
                      widget._movie['title'],
                      style: AppTextStyles.gameTitle,
                    ),
                    Text(
                      widget._movie['subtitle'],
                      style: AppTextStyles.gameSubtitle,
                    ),
                  ],
                )
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Text(widget._movie['description']),
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
                    widget._movieDetailsPageBloc.add(
                      PriceSearchEvent(
                          "Busque os preços para o jogo '${widget._movie['title']}'"),
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
