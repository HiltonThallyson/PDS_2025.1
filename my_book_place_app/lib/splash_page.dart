import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';

class SplashPage extends StatefulWidget {
  const SplashPage({super.key});

  @override
  State<SplashPage> createState() => _SplashPageState();
}

class _SplashPageState extends State<SplashPage> {
  @override
  void initState() {
    _loadAuthPage();
    super.initState();
  }

  void _loadAuthPage() async {
    Future.delayed(const Duration(seconds: 2))
        .then((_) => Modular.to.pushReplacementNamed("/auth/"));
  }

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
          child: Column(
        children: [
          Icon(Icons.book, size: 100),
          CircularProgressIndicator(
            color: Colors.purple,
          ),
        ],
      )),
    );
  }
}
