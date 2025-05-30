import 'package:flutter_bloc/flutter_bloc.dart';

import '../../domain/infra/repositories/image_gen_repository.dart';
import 'events/gen_image_events.dart';
import 'states/gen_image_states.dart';

class GenImageBloc extends Bloc<GenImageEvent, GenImageState> {
  final ImageGenRepository _imageGenRepository;

  GenImageBloc(this._imageGenRepository) : super(GenImageInitialState()) {
    on<GenerateImageEvent>((event, emit) async {
      emit(GenImageLoadingState());
      try {
        final imageBytes =
            await _imageGenRepository.generateImage(event.prompt);

        emit(GenImageSuccessState(imageBytes));
      } catch (e) {
        emit(GenImageErrorState(e.toString()));
      }
    });
  }
}
