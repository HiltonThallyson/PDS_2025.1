import 'package:flutter_modular/flutter_modular.dart';

import '../../data/repositories/googlebook_repository_impl.dart';
import '../../ui/catalogue_page.dart';
import '../blocs/catalogue_page_bloc.dart';
import '../infra/repositories/googlebook_repository_interface.dart';

class CatalogueModule extends Module {
  @override
  void binds(i) {
    i.addSingleton<CataloguePageBloc>(CataloguePageBloc.new);
    i.add<GoogleBookRepositoryInterface>(GoogleBookRepositoryImpl.new);
  }

  @override
  void routes(r) {
    r.child("/", child: (context) => const CataloguePage());
  }
}
