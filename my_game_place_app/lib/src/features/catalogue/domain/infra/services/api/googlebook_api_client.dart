import '../../../models/game.dart';

abstract interface class FreeGamesApiClient {
  Future<List<Game>> getGameListByQty(int qty);
  // Future<List<Game>> getBookListByAuthor(String author);
  // Future<List<Game>> getBookListByTitle(String title);
}
