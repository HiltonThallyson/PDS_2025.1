import 'package:flutter/foundation.dart';

abstract class GenImageState {}

class GenImageInitialState extends GenImageState {}

class GenImageLoadingState extends GenImageState {}

class GenImageSuccessState extends GenImageState {
  final Uint8List imageUrl;

  GenImageSuccessState(this.imageUrl);
}

class GenImageErrorState extends GenImageState {
  final String errorMessage;

  GenImageErrorState(this.errorMessage);
}
