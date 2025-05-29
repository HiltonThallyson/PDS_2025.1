import 'package:flutter/foundation.dart';

abstract interface class ImageGenRepository {
  Future<Uint8List> generateImage(String prompt);
}
