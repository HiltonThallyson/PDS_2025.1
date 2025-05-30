import 'package:flutter_modular/flutter_modular.dart';

import '../../auth/domain/entities/user.dart';
import '../data/external/repositories/price_search_repository_impl.dart';
import '../data/external/services/api/price_search_api_client_impl.dart';
import '../domain/infra/repositories/price_search_repository.dart';
import '../domain/infra/services/api/price_search_api_client.dart';
import '../presentation/ui/price_search_page.dart';
import '../presentation/blocs/price_search_bloc.dart';

class PriceSearchModule extends Module {
  @override
  void binds(i) {
    i.add<PriceSearchBloc>(PriceSearchBloc.new);
    i.addSingleton<User>(User.new);
    i.add<PriceSearchApiClient>(PriceSearchApiClientImpl.new);
    i.add<PriceSearchRepository>(PriceSearchRepositoryImpl.new);
  }

  @override
  void routes(r) {
    r.child('/', child: (context) => PriceSearchPage());
  }
}
