import 'package:flutter/material.dart';

import '../../../../core/themes/app_textstyles.dart';
import '../../models/book.dart';

class BookCard extends StatelessWidget {
  Map<String, dynamic> _book;
  Size _size;
  BookCard({required Map<String, dynamic> book, required Size size, super.key})
      : _book = book,
        _size = size;

  @override
  Widget build(BuildContext context) {
    final originalUrl = _book["thumbnail"];
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
              child: Container(
                height: 150,
                width: _size.width,
                decoration: BoxDecoration(
                  image: DecorationImage(
                    image: NetworkImage(proxyUrl),
                    fit: BoxFit.fill,
                  ),
                ),
              ),
              // child: Image.network(
              //   proxyUrl,
              //   height: 150,
              //   width: 150,
              //   fit: BoxFit.fill,
            ),
            Positioned(
              top: 200,
              child: Expanded(
                child: SingleChildScrollView(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        _book["title"],
                        style: AppTextStyles.bookTitle,
                      ),
                      const SizedBox(
                        height: 3,
                      ),
                      Text(
                        _book["subtitle"],
                        style: AppTextStyles.bookSubtitle,
                      ),
                      const SizedBox(
                        height: 3,
                      ),
                    ],
                  ),
                ),
              ),
            ),
            Positioned(
              bottom: 0,
              child: Text(
                "Categorias: ${_book["categories"].join(", ")}",
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
