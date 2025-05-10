import 'package:flutter_modular/flutter_modular.dart';

import '../../../../core/entities/user.dart';
import '../../../home/interactor/module/home_module.dart';
import '../../data/repositories/auth_repository.dart';
import '../../data/repositories/auth_repository_interface.dart';
import '../../ui/auth_page.dart';
import '../bloc/auth_bloc.dart';

class AuthModule extends Module {
  @override
  void binds(i) {
    i.addSingleton<AuthBloc>(AuthBloc.new);
    i.add<AuthRepositoryInterface>(AuthRepository.new);
    i.addSingleton<User>(User.new);
  }

  @override
  void routes(r) {
    r.child('/', child: (context) => AuthPage());
    // r.module("/home", module: HomeModule());
  }
}
