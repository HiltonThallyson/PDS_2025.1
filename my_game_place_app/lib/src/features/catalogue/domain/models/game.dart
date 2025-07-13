class Game {
  String _title;
  List<String> _developers;
  String _publisher;
  String _thumbnail;
  String _subtitle;
  String _description;
  List<String> _categories;

  Game({
    required String title,
    required List<String> developers,
    required String description,
    required String publisher,
    required String thumbnail,
    required String subtitle,
    required List<String> categories,
  })  : _title = title,
        _developers = developers,
        _description = description,
        _publisher = publisher,
        _thumbnail = thumbnail,
        _subtitle = subtitle,
        _categories = categories;

  Map<String, dynamic> getGameInfo() {
    return {
      'title': _title,
      'developers': _developers,
      'description': _description,
      'publisher': _publisher,
      'thumbnail': _thumbnail,
      'subtitle': _subtitle,
      'categories': _categories,
    };
  }
}
