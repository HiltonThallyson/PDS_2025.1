import 'package:flutter_bloc/flutter_bloc.dart';

import '../../domain/models/game.dart';
import '../../domain/infra/repositories/googlebook_repository_interface.dart';
import 'catalogue_page_event.dart';
import 'catalogue_page_state.dart';

class CataloguePageBloc extends Bloc<CataloguePageEvent, CataloguePageState> {
  final FreeGamesRepositoryInterface _googleBookRepository;

  List<Game> _bookList = [];

  CataloguePageBloc(this._googleBookRepository)
      : super(CataloguePageLoadingState()) {
    on<CataloguePageLoadEvent>((event, emit) async {
      emit(CataloguePageLoadingState());
      if (_bookList.isNotEmpty) {
        emit(CataloguePageLoadedState(_bookList));
        return;
      }
      try {
        _bookList = await _googleBookRepository.getGameListByQty(event.qty);

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
