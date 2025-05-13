import 'package:flutter_modular/flutter_modular.dart';

import '../../ui/catalogue_page.dart';

class CatalogueModule extends Module {
  @override
  void binds(i) {}

  @override
  void routes(r) {
    r.child("/", child: (context) => const CataloguePage());
  }
}
