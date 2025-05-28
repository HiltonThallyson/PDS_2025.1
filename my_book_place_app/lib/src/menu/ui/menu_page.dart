import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../../core/themes/app_textstyles.dart';
import '../../general_widgets/mybookplace_appbar.dart';
import 'widgets/menu_card.dart';

class MenuPage extends StatelessWidget with MyBookPlaceAppBarMixin {
  const MenuPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.indigo,
      appBar: buildAppBar(context),
      body: Center(
        child: SingleChildScrollView(
          padding: const EdgeInsets.symmetric(vertical: 0, horizontal: 10),
          child: Wrap(
            alignment: WrapAlignment.start,
            runAlignment: WrapAlignment.center,
            spacing: 10,
            runSpacing: 10,
            children: [
              MenuCard(
                  route: "/catalogue/",
                  icon: Icons.menu_book_rounded,
                  title: "Catalogo"),
              MenuCard(
                  route: "/search-price/",
                  icon: Icons.price_check_rounded,
                  title: "Ofertas"),
              MenuCard(
                route: "/gen-image/",
                title: "Gerar imagem",
                icon: Icons.image,
              ),
            ],
          ),
        ),
      ),
    );
  }
}
