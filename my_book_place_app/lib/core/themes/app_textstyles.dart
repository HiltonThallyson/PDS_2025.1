import 'package:flutter/material.dart';

abstract class AppTextStyles {
  static const loginButtonText = TextStyle(
    fontSize: 16,
    fontWeight: FontWeight.bold,
    color: Colors.white,
  );
  static const textButtonsStyle = TextStyle(
    fontSize: 16,
    fontWeight: FontWeight.w500,
    color: Colors.indigo,
  );

  static const menuCardText = TextStyle(
    fontSize: 30,
    fontWeight: FontWeight.w500,
    color: Colors.indigo,
  );

  static const bookTitle = TextStyle(
    fontSize: 16,
    fontWeight: FontWeight.w500,
    color: Colors.black,
  );

  static const bookSubtitle = TextStyle(
    fontSize: 12,
    fontWeight: FontWeight.w400,
    color: Colors.black,
  );
  static const offerTitle = TextStyle(
    fontSize: 16,
    fontWeight: FontWeight.w700,
    color: Colors.black,
  );

  static const offerSubtitle = TextStyle(
    fontSize: 12,
    fontWeight: FontWeight.w400,
    color: Colors.black,
  );

  static const offerLink = TextStyle(
    fontSize: 12,
    fontWeight: FontWeight.w400,
    color: Colors.indigo,
  );
}
