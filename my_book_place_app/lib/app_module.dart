import 'package:flutter_modular/flutter_modular.dart';

import 'src/auth/interactor/modules/auth_module.dart';
import 'src/book-details/interactor/modules/book_details_module.dart';
import 'src/catalogue/interactor/modules/catalogue_module.dart';
import 'src/menu/interactor/modules/menu_module.dart';
import 'src/price_search/interactor/modules/price_search_module.dart';
import 'splash_page.dart';

class AppModule extends Module {
  @override
  void binds(i) {}

  @override
  void routes(r) {
    r.child("/", child: (context) => const SplashPage());
    r.module("/menu", module: MenuModule());
    r.module("/catalogue", module: CatalogueModule());
    r.module("/auth", module: AuthModule());
    r.module("/search-price", module: PriceSearchModule());
    r.module("/book-details", module: BookDetailsModule());
  }
}
