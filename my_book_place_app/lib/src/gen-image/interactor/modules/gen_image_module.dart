import 'package:flutter_modular/flutter_modular.dart';

import '../../../../core/entities/user.dart';
import '../../data/repositories/image_gen_repository_impl.dart';
import '../../infra/image_gen_repository.dart';
import '../../ui/gen_image_page.dart';
import '../blocs/gen_image_bloc.dart';

class GenImageModule extends Module {
  @override
  void binds(i) {
    i.add<GenImageBloc>(GenImageBloc.new);
    i.add<ImageGenRepository>(ImageGenRepositoryImpl.new);
    i.add<User>(User.new);
  }

  @override
  void routes(r) {
    r.child("/", child: (context) => GenImagePage());
  }
}
