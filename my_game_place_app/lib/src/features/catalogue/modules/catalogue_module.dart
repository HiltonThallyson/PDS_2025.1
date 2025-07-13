import 'package:flutter_modular/flutter_modular.dart';

import '../../auth/domain/entities/user.dart';
import '../data/external/repositories/googlebook_repository_impl.dart';
import '../data/external/services/api/freegames_api_client_impl.dart';
import '../domain/infra/services/api/googlebook_api_client.dart';
import '../presentation/ui/catalogue_page.dart';
import '../presentation/blocs/catalogue_page_bloc.dart';
import '../domain/infra/repositories/googlebook_repository_interface.dart';

class CatalogueModule extends Module {
  @override
  void binds(i) {
    i.addSingleton<CataloguePageBloc>(CataloguePageBloc.new);
    i.addSingleton<User>(User.new);
    i.add<FreeGamesApiClient>(FreeGamesApiClientImpl.new);
    i.add<FreeGamesRepositoryInterface>(FreeGamesRepositoryImpl.new);
  }

  @override
  void routes(r) {
    r.child("/", child: (context) => const CataloguePage());
  }
}
