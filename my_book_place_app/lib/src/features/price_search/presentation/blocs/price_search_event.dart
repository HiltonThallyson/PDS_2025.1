abstract class PriceSearchEvent {}

class SendPromptEvent extends PriceSearchEvent {
  final String prompt;

  SendPromptEvent(this.prompt);
}
