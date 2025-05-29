import 'package:bloc/bloc.dart';

import '../infra/ai_agent_repository.dart';
import 'events/price_search_event.dart';
import 'states/price_search_state.dart';

class PriceSearchBloc extends Bloc<PriceSearchEvent, PriceSearchState> {
  final AiAgentRepository _repository;

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
