abstract interface class AiAgentRepository {
  Future<List<dynamic>> getPriceWithAgent(String prompt);
}
