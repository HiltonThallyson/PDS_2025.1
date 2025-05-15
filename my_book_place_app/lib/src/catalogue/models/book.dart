// ignore_for_file: public_member_api_docs, sort_constructors_first
class Book {
  String _title;
  List<String> _authors;
  String _publisher;
  String _thumbnail;
  String _subtitle;
  String _description;
  List<String> _categories;

  Book({
    required String title,
    required List<String> authors,
    required String description,
    required String publisher,
    required String thumbnail,
    required String subtitle,
    required List<String> categories,
  })  : _title = title,
        _authors = authors,
        _description = description,
        _publisher = publisher,
        _thumbnail = thumbnail,
        _subtitle = subtitle,
        _categories = categories;

  Map<String, dynamic> getBookInfo() {
    return {
      'title': _title,
      'authors': _authors,
      'description': _description,
      'publisher': _publisher,
      'thumbnail': _thumbnail,
      'subtitle': _subtitle,
      'categories': _categories,
    };
  }
}
