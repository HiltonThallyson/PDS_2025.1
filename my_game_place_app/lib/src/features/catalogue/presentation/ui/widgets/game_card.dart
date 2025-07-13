import 'package:flutter/material.dart';

import '../../../../../../core/themes/app_textstyles.dart';
import '../../../domain/models/game.dart';

class GameCard extends StatelessWidget {
  Map<String, dynamic> _game;
  Size _size;
  GameCard({required Map<String, dynamic> game, required Size size, super.key})
      : _game = game,
        _size = size;

  @override
  Widget build(BuildContext context) {
    final originalUrl = _game["thumbnail"];
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
                      _game["title"],
                      style: AppTextStyles.gameTitle,
                    ),
                    const SizedBox(
                      height: 3,
                    ),
                    Text(
                      _game["subtitle"],
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
                "Categorias: ${_game["categories"].join(", ")}",
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
