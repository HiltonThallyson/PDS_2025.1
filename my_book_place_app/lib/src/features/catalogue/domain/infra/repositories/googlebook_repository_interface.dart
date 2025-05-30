import '../../models/book.dart';

abstract class GoogleBookRepositoryInterface {
  Future<List<Book>> getBookListByQty(int qty);
  Future<List<Book>> getBookListByAuthor(String author);
  Future<List<Book>> getBookListByTitle(String title);
}
