import 'package:flutter_bloc/flutter_bloc.dart';

import '../../../price_search/interactor/infra/ai_agent_repository.dart';
import 'events/book_details_page_event.dart';
import 'states/book_details_page_state.dart';

class BookDetailsPageBloc
    extends Bloc<BookDetailsPageEvent, BookDetailsPageState> {
  final AiAgentRepository _aiAgentRepository;

  BookDetailsPageBloc(this._aiAgentRepository)
      : super(BookDetailsPageInitial()) {
    on<PriceSearchEvent>((event, emit) async {
      emit(BookDetailsPageLoading());
      try {
        final offers =
            await _aiAgentRepository.getPriceWithAgent(event.searchText);
        emit(BookDetailsPageLoadedOffers(offers));
      } catch (e) {
        emit(BookDetailsPageError(e.toString()));
      }
    });
  }
}
