import 'package:flutter_modular/flutter_modular.dart';

import '../../data/ai_agent_repository_impl.dart';
import '../infra/ai_agent_repository.dart';
import '../../ui/price_search_page.dart';
import '../blocs/price_search_bloc.dart';

class PriceSearchModule extends Module {
  @override
  void binds(i) {
    i.add<PriceSearchBloc>(PriceSearchBloc.new);
    i.add<AiAgentRepository>(AiAgentRepositoryImpl.new);
  }

  @override
  void routes(r) {
    r.child('/', child: (context) => PriceSearchPage());
  }
}
