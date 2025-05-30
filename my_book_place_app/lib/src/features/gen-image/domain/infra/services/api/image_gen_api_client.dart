import 'dart:typed_data';

abstract interface class ImageGenApiClient {
  Future<Uint8List> generateImage(String prompt);
}
