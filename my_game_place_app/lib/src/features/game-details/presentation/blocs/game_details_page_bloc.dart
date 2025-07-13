import 'package:flutter_bloc/flutter_bloc.dart';

import '../../../price_search/domain/infra/repositories/price_search_repository.dart';
import 'game_details_page_event.dart';
import 'game_details_page_state.dart';

class GameDetailsPageBloc
    extends Bloc<GameDetailsPageEvent, GameDetailsPageState> {
  final PriceSearchRepository _priceSearchRepository;

  GameDetailsPageBloc(this._priceSearchRepository)
      : super(GameDetailsPageInitial()) {
    on<PriceSearchEvent>((event, emit) async {
      emit(GameDetailsPageLoading());
      try {
        final offers =
            await _priceSearchRepository.getPriceWithAgent(event.searchText);
        emit(GameDetailsPageLoadedOffers(offers));
      } catch (e) {
        emit(GameDetailsPageError(e.toString()));
      }
    });
  }
}
