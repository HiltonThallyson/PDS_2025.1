import '../../models/game.dart';

abstract class FreeGamesRepositoryInterface {
  Future<List<Game>> getGameListByQty(int qty);
  // Future<List<Game>> getGameListByAuthor(String author);
  // Future<List<Game>> getGameListByTitle(String title);
}
