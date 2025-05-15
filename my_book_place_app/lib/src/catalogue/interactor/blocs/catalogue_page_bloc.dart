import 'package:flutter_bloc/flutter_bloc.dart';

import '../../models/book.dart';
import '../infra/repositories/googlebook_repository_interface.dart';
import 'events/catalogue_page_event.dart';
import 'states/catalogue_page_state.dart';

class CataloguePageBloc extends Bloc<CataloguePageEvent, CataloguePageState> {
  final GoogleBookRepositoryInterface _googleBookRepository;

  List<Book> _bookList = [];

  CataloguePageBloc(this._googleBookRepository)
      : super(CataloguePageLoadingState()) {
    on<CataloguePageLoadEvent>((event, emit) async {
      emit(CataloguePageLoadingState());
      if (_bookList.isNotEmpty) {
        emit(CataloguePageLoadedState(_bookList));
        return;
      }
      try {
        _bookList = await _googleBookRepository.getBookListByQty(event.qty);

        emit(
          CataloguePageLoadedState(
            _bookList,
          ),
        );
      } catch (e) {
        emit(CataloguePageErrorState(e.toString()));
      }
    });
  }
}
