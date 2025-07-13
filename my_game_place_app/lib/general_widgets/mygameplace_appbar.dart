import 'package:flutter/material.dart';

mixin MyGamePlaceAppBarMixin {
  AppBar buildAppBar(BuildContext context) {
    return AppBar(
      backgroundColor: Colors.transparent,
      elevation: 0,
      foregroundColor: Colors.white,
      centerTitle: true,
      title: const Text(
        "MyGamePlace",
        style: TextStyle(color: Colors.white, fontSize: 40),
      ),
    );
  }
}
