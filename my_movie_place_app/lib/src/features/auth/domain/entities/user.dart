class User {
  String? _username;
  String? _nickname;
  String? _email;
  String? _token;

  static final User _instance = User._internal();
  factory User() {
    return _instance;
  }
  User._internal();

  String? get username => _username;
  String? get nickname => _nickname;
  String? get email => _email;
  String? get token => _token;

  set username(String? username) => _username = username;
  set nickname(String? nickname) => _nickname = nickname;
  set email(String? email) => _email = email;
  set token(String? token) => _token = token;

  void fromJson(Map<String, dynamic> json) {
    _username = json['username'];
    _nickname = json['nickname'];
    _email = json['email'];
    _token = json['token'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['username'] = _username;
    data['nickname'] = _nickname;
    data['email'] = _email;
    return data;
  }

  void clear() {
    _username = null;
    _nickname = null;
    _email = null;
    _token = null;
  }
}
