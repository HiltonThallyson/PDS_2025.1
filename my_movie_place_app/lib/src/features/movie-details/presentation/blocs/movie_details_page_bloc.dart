import 'package:flutter_bloc/flutter_bloc.dart';

import '../../../price_search/domain/infra/repositories/price_search_repository.dart';
import 'movie_details_page_event.dart';
import 'movie_details_page_state.dart';

class MovieDetailsPageBloc
    extends Bloc<MovieDetailsPageEvent, MovieDetailsPageState> {
  final PriceSearchRepository _priceSearchRepository;

  MovieDetailsPageBloc(this._priceSearchRepository)
      : super(MovieDetailsPageInitial()) {
    on<PriceSearchEvent>((event, emit) async {
      emit(MovieDetailsPageLoading());
      try {
        final offers =
            await _priceSearchRepository.getPriceWithAgent(event.searchText);
        emit(MovieDetailsPageLoadedOffers(offers));
      } catch (e) {
        emit(MovieDetailsPageError(e.toString()));
      }
    });
  }
}
