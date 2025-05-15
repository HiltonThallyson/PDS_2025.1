import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../../core/themes/app_textstyles.dart';
import '../../general_widgets/mybookplace_appbar.dart';

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
            alignment: WrapAlignment.center,
            spacing: 10,
            runSpacing: 10,
            children: [
              InkWell(
                onTap: () => Modular.to.pushNamed("/catalogue/"),
                child: Card(
                  elevation: 5,
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10)),
                  color: Colors.white,
                  child: const SizedBox(
                    height: 300,
                    width: 300,
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Text(
                          "Catalogo",
                          style: AppTextStyles.menuCardText,
                        ),
                        Icon(
                          Icons.menu_book_rounded,
                          color: Colors.indigo,
                          size: 80,
                        )
                      ],
                    ),
                  ),
                ),
              ),
              InkWell(
                onTap: () => Modular.to.pushNamed("/search-price"),
                child: Card(
                  elevation: 5,
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10)),
                  color: Colors.white,
                  child: const SizedBox(
                    height: 300,
                    width: 300,
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Text(
                          "Ofertas",
                          style: AppTextStyles.menuCardText,
                        ),
                        Icon(
                          Icons.price_check_rounded,
                          color: Colors.indigo,
                          size: 80,
                        )
                      ],
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
