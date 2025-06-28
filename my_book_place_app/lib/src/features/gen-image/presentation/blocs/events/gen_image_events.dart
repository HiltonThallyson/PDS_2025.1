abstract class GenImageEvent {}

class GenerateImageEvent extends GenImageEvent {
  final String prompt;

  GenerateImageEvent(this.prompt);
}
