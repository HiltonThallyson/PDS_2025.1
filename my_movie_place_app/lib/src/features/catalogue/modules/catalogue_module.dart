import 'package:flutter_modular/flutter_modular.dart';

import '../../auth/domain/entities/user.dart';
import '../data/external/repositories/tmdb_repository_impl.dart';
import '../data/external/services/api/tmdb_api_client_impl.dart';
import '../domain/infra/services/api/tmdb_api_client.dart';
import '../presentation/ui/catalogue_page.dart';
import '../presentation/blocs/catalogue_page_bloc.dart';
import '../domain/infra/repositories/tmdb_repository_interface.dart';

class CatalogueModule extends Module {
  @override
  void binds(i) {
    i.addSingleton<CataloguePageBloc>(CataloguePageBloc.new);
    i.addSingleton<User>(User.new);
    i.add<TMDBApiService>(TMDBApiClient.new);
    i.add<TMDBRepositoryInterface>(TMDBRepositoryImpl.new);
  }

  @override
  void routes(r) {
    r.child("/", child: (context) => const CataloguePage());
  }
}
