import 'package:flutter/material.dart';

mixin MyBookPlaceAppBarMixin {
  AppBar buildAppBar(BuildContext context) {
    return AppBar(
      backgroundColor: Colors.transparent,
      elevation: 0,
      centerTitle: true,
      title: const Text(
        "MyBookPlace",
        style: TextStyle(color: Colors.white, fontSize: 40),
      ),
    );
  }
}
