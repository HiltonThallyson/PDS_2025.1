class Offer {
  String _title;
  String _price;
  String _url;
  String? _imageUrl;

  Offer(this._title, this._price, this._url, this._imageUrl);

  Map<String, dynamic> getOfferInfo() {
    return {
      'title': _title,
      'price': _price,
      'url': _url,
      'imageUrl': _imageUrl,
    };
  }
}
