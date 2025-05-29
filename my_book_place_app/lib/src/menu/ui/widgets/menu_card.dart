import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../../../core/themes/app_textstyles.dart';

class MenuCard extends StatelessWidget {
  String _route;
  IconData _icon;
  String _title;

  MenuCard({
    super.key,
    required String route,
    required IconData icon,
    required String title,
  })  : _route = route,
        _icon = icon,
        _title = title;

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () => Modular.to.pushNamed(_route),
      child: Card(
        elevation: 5,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
        color: Colors.white,
        child: SizedBox(
          height: 300,
          width: 300,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                _title,
                style: AppTextStyles.menuCardText,
              ),
              Icon(
                _icon,
                color: Colors.indigo,
                size: 80,
              )
            ],
          ),
        ),
      ),
    );
  }
}
