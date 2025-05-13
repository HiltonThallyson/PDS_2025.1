import 'package:flutter_modular/flutter_modular.dart';

import '../../../../core/entities/user.dart';
import '../../data/external/auth_repository_impl.dart';
import '../infra/auth_repository_interface.dart';
import '../../ui/auth_page.dart';
import '../blocs/auth_bloc.dart';

class AuthModule extends Module {
  @override
  void binds(i) {
    i.addSingleton<AuthBloc>(AuthBloc.new);
    i.add<AuthRepositoryInterface>(AuthRepositoryImpl.new);
    i.addSingleton<User>(User.new);
  }

  @override
  void routes(r) {
    r.child('/', child: (context) => AuthPage());
  }
}
