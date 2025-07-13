import 'package:bloc/bloc.dart';

import '../../domain/infra/repositories/price_search_repository.dart';
import 'price_search_event.dart';
import 'price_search_state.dart';

class PriceSearchBloc extends Bloc<PriceSearchEvent, PriceSearchState> {
  final PriceSearchRepository _repository;

  PriceSearchBloc(this._repository) : super(PriceSearchInitial()) {
    on<SendPromptEvent>((event, emit) async {
      try {
        emit(PriceSearchLoading());
        final result = await _repository.getPriceWithAgent(event.prompt);
        emit(PriceSearchResult(result));
      } catch (e) {
        emit(PriceSearchError(
            "Houve um problema ao buscar ofertas. Tente novamente."));
      }
    });
  }
}
