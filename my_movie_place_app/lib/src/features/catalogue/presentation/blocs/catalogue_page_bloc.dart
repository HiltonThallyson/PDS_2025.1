import 'package:flutter_bloc/flutter_bloc.dart';

import '../../domain/models/movie.dart';
import '../../domain/infra/repositories/tmdb_repository_interface.dart';
import 'catalogue_page_event.dart';
import 'catalogue_page_state.dart';

class CataloguePageBloc extends Bloc<CataloguePageEvent, CataloguePageState> {
  final TMDBRepositoryInterface _tmdbRepository;

  List<Movie> _bookList = [];

  CataloguePageBloc(this._tmdbRepository) : super(CataloguePageLoadingState()) {
    on<CataloguePageLoadEvent>((event, emit) async {
      emit(CataloguePageLoadingState());
      if (_bookList.isNotEmpty) {
        emit(CataloguePageLoadedState(_bookList));
        return;
      }
      try {
        _bookList = await _tmdbRepository.getMovieListByQty(event.qty);

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
