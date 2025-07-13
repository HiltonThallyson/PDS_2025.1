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
    return Scaffold(
      body: SizedBox(
        height: MediaQuery.of(context).size.height,
        width: double.maxFinite,
        child: const Center(
            child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.gamepad_rounded, size: 100),
            SizedBox(
              height: 10,
            ),
            CircularProgressIndicator(
              color: Colors.purple,
            ),
          ],
        )),
      ),
    );
  }
}
