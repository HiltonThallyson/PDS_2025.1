import 'package:flutter_modular/flutter_modular.dart';

import '../../auth/domain/entities/user.dart';
import '../data/external/repositories/image_gen_repository_impl.dart';
import '../data/external/services/api/image_gen_api_client_impl.dart';
import '../domain/infra/repositories/image_gen_repository.dart';
import '../domain/infra/services/api/image_gen_api_client.dart';
import '../presentation/ui/gen_image_page.dart';
import '../presentation/blocs/gen_image_bloc.dart';

class GenImageModule extends Module {
  @override
  void binds(i) {
    i.add<GenImageBloc>(GenImageBloc.new);
    i.add<ImageGenApiClient>(ImageGenApiClientImpl.new);
    i.add<ImageGenRepository>(ImageGenRepositoryImpl.new);
    i.add<User>(User.new);
  }

  @override
  void routes(r) {
    r.child("/", child: (context) => GenImagePage());
  }
}
