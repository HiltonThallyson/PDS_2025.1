import 'package:flutter/material.dart';

import '../../../../../../core/themes/app_textstyles.dart';
import '../../../domain/models/movie.dart';

class MovieCard extends StatelessWidget {
  Map<String, dynamic> _movie;
  Size _size;
  MovieCard(
      {required Map<String, dynamic> movie, required Size size, super.key})
      : _movie = movie,
        _size = size;

  @override
  Widget build(BuildContext context) {
    final originalUrl = _movie["thumbnail"];
    final proxyUrl = "http://corsproxy.io/?" + Uri.encodeFull(originalUrl);
    return Card(
      color: Colors.white,
      child: Container(
        height: _size.height,
        width: _size.width,
        margin: const EdgeInsets.all(10),
        child: Stack(
          children: [
            Positioned(
              top: 0,
              // child: Container(
              //   height: 150,
              //   width: _size.width,
              //   decoration: BoxDecoration(
              //     image: DecorationImage(
              //       image: NetworkImage(proxyUrl),
              //       fit: BoxFit.fill,
              //     ),
              //   ),
              // ),
              child: Image.network(
                proxyUrl,
                height: 150,
                width: 150,
                fit: BoxFit.fill,
              ),
            ),
            Positioned(
              top: 200,
              child: SingleChildScrollView(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      _movie["title"],
                      style: AppTextStyles.gameTitle,
                    ),
                    const SizedBox(
                      height: 3,
                    ),
                    Text(
                      _movie["subtitle"],
                      style: AppTextStyles.gameSubtitle,
                    ),
                    const SizedBox(
                      height: 3,
                    ),
                  ],
                ),
              ),
            ),
            Positioned(
              bottom: 0,
              child: Text(
                "Categorias: ${_movie["categories"].join(", ")}",
                textAlign: TextAlign.end,
                overflow: TextOverflow.ellipsis,
              ),
            ),
            Positioned(
                right: 5,
                top: 160,
                child: IconButton(
                    onPressed: () {},
                    icon: const Icon(Icons.favorite_border_rounded)))
          ],
        ),
      ),
    );
  }
}
