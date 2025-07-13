import 'package:flutter_modular/flutter_modular.dart';

import '../presentation/ui/menu_page.dart';

class MenuModule extends Module {
  @override
  void binds(i) {}

  @override
  void routes(r) {
    r.child("/", child: (context) => const MenuPage());
  }
}
