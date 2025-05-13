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
    final proxyUrl = "https://corsproxy.io/?" + Uri.encodeFull(originalUrl);
    return Card(
      color: Colors.white,
      child: Container(
        height: _size.height,
        width: _size.width,
        margin: const EdgeInsets.all(10),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Image.network(
              proxyUrl,
              height: 150,
              width: 150,
              fit: BoxFit.fill,
            ),
            const SizedBox(
              height: 5,
            ),
            Expanded(
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
            Text(
              "Categorias: ${_book["categories"].join(", ")}",
              textAlign: TextAlign.end,
              overflow: TextOverflow.ellipsis,
            ),
          ],
        ),
      ),
    );
  }
}
