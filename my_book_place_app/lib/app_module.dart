import 'package:flutter_modular/flutter_modular.dart';

import 'features/auth/interactor/bloc/auth_bloc.dart';
import 'features/auth/interactor/module/auth_module.dart';
import 'features/home/interactor/module/home_module.dart';
import 'splash_page.dart';

class AppModule extends Module {
  @override
  void binds(i) {}

  @override
  void routes(r) {
    r.child("/", child: (context) => const SplashPage());
    r.module("/home", module: HomeModule());
    r.module("/auth", module: AuthModule());
  }
}
