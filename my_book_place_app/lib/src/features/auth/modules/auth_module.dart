import 'package:flutter_modular/flutter_modular.dart';

import '../data/external/services/api/auth_api_client_impl.dart';
import '../domain/entities/user.dart';
import '../data/external/repositories/auth_repository_impl.dart';
import '../domain/infra/services/api/auth_api_client.dart';
import '../presentation/blocs/auth_bloc.dart';
import '../domain/infra/repositories/auth_repository_interface.dart';
import '../presentation/ui/auth_page.dart';

class AuthModule extends Module {
  @override
  void binds(i) {
    i.addSingleton<AuthBloc>(AuthBloc.new);
    i.add<AuthApiClient>(AuthApiClientImpl.new);
    i.add<AuthRepositoryInterface>(AuthRepositoryImpl.new);
    i.addSingleton<User>(User.new);
  }

  @override
  void routes(r) {
    r.child('/', child: (context) => AuthPage());
  }
}
