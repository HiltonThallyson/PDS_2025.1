import 'package:flutter_modular/flutter_modular.dart';

import '../../../price_search/data/ai_agent_repository_impl.dart';
import '../../../price_search/interactor/infra/ai_agent_repository.dart';
import '../../ui/book_details_page.dart';
import '../blocs/book_details_page_bloc.dart';

class BookDetailsModule extends Module {
  @override
  void binds(i) {
    i.addSingleton<BookDetailsPageBloc>(BookDetailsPageBloc.new);
    i.add<AiAgentRepository>(AiAgentRepositoryImpl.new);
  }

  @override
  void routes(r) {
    r.child(
      "/",
      child: (context) => BookDetailsPage(
        book: Modular.args.data,
      ),
    );
  }
}
