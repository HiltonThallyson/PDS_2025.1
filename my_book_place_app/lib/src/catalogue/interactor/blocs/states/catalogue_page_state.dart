import '../../../models/book.dart';

abstract class CataloguePageState {}

class CataloguePageLoadingState extends CataloguePageState {}

class CataloguePageLoadedState extends CataloguePageState {
  final List<Book> bookList;
  CataloguePageLoadedState(this.bookList);
}

class CataloguePageErrorState extends CataloguePageState {
  final String errorMessage;
  CataloguePageErrorState(this.errorMessage);
}
