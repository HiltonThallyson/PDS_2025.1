import 'package:flutter_modular/flutter_modular.dart';

import '../../ui/auth_page.dart';
import '../bloc/auth_bloc.dart';

class AuthModule extends Module {
  @override
  void binds(i) {
    i.addSingleton<AuthBloc>(AuthBloc.new);
  }

  @override
  void routes(r) {
    r.child('/', child: (context) => AuthPage());
  }
}
