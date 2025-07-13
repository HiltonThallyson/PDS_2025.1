import 'package:flutter/material.dart';

mixin MyMoviePlaceAppBarMixin {
  AppBar buildAppBar(BuildContext context) {
    return AppBar(
      backgroundColor: Colors.transparent,
      elevation: 0,
      foregroundColor: Colors.white,
      centerTitle: true,
      title: const Text(
        "MyMoviePlace",
        style: TextStyle(color: Colors.white, fontSize: 40),
      ),
    );
  }
}
