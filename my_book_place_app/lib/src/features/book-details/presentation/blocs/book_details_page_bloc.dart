import 'package:flutter_bloc/flutter_bloc.dart';

import '../../../price_search/domain/infra/repositories/price_search_repository.dart';
import 'book_details_page_event.dart';
import 'book_details_page_state.dart';

class BookDetailsPageBloc
    extends Bloc<BookDetailsPageEvent, BookDetailsPageState> {
  final PriceSearchRepository _priceSearchRepository;

  BookDetailsPageBloc(this._priceSearchRepository)
      : super(BookDetailsPageInitial()) {
    on<PriceSearchEvent>((event, emit) async {
      emit(BookDetailsPageLoading());
      try {
        final offers =
            await _priceSearchRepository.getPriceWithAgent(event.searchText);
        emit(BookDetailsPageLoadedOffers(offers));
      } catch (e) {
        emit(BookDetailsPageError(e.toString()));
      }
    });
  }
}
