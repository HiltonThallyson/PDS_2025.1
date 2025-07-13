import 'package:flutter_modular/flutter_modular.dart';

import '../../price_search/data/external/repositories/price_search_repository_impl.dart';
import '../../price_search/data/external/services/api/price_search_api_client_impl.dart';
import '../../price_search/domain/infra/repositories/price_search_repository.dart';
import '../../price_search/domain/infra/services/api/price_search_api_client.dart';
import '../presentation/ui/movie_details_page.dart';
import '../presentation/blocs/movie_details_page_bloc.dart';

class MovieDetailsModule extends Module {
  @override
  void binds(i) {
    i.addSingleton<MovieDetailsPageBloc>(MovieDetailsPageBloc.new);
    i.add<PriceSearchApiClient>(PriceSearchApiClientImpl.new);
    i.add<PriceSearchRepository>(PriceSearchRepositoryImpl.new);
  }

  @override
  void routes(r) {
    r.child(
      "/",
      child: (context) => MovieDetailsPage(
        movie: Modular.args.data,
      ),
    );
  }
}
