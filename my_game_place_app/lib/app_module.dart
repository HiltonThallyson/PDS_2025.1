import 'package:flutter_modular/flutter_modular.dart';

import 'src/features/auth/modules/auth_module.dart';
import 'src/features/game-details/modules/game_details_module.dart';
import 'src/features/catalogue/modules/catalogue_module.dart';
import 'src/features/gen-image/modules/gen_image_module.dart';
import 'src/features/menu/modules/menu_module.dart';
import 'src/features/price_search/modules/price_search_module.dart';
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
    r.module("/game-details", module: GameDetailsModule());
    r.module("/gen-image", module: GenImageModule());
  }
}
