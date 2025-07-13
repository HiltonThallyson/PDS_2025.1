import 'dart:io';

import 'package:flutter/material.dart';
import 'package:url_launcher/link.dart';

import '../../../../../../core/themes/app_textstyles.dart';

class OfferCard extends StatelessWidget {
  final Map<String, dynamic> offer;
  const OfferCard({required this.offer, super.key});

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 5,
      shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(10),
          side: const BorderSide(color: Colors.indigo, width: 2)),
      child: Padding(
        padding: const EdgeInsets.all(10.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            offer['imageUrl'].toString().isNotEmpty
                ? ClipRRect(
                    borderRadius: BorderRadius.circular(10),
                    child: Image.network(
                      offer['imageUrl'],
                      height: 100,
                      width: 100,
                      fit: BoxFit.cover,
                    ),
                  )
                : Container(
                    height: 100,
                    width: 100,
                    decoration: BoxDecoration(
                      color: Colors.grey[300],
                      borderRadius: BorderRadius.circular(10),
                    ),
                  ),
            Text(
              offer['title'],
              style: AppTextStyles.offerTitle,
            ),
            const SizedBox(
              height: 20,
            ),
            Align(
              alignment: Alignment.centerLeft,
              child: Text(
                "Preço: R\$ ${offer['price']}",
                style: AppTextStyles.offerSubtitle,
              ),
            ),
            const SizedBox(
              height: 5,
            ),
            Link(
              target: LinkTarget.self,
              uri: Uri.parse(offer['url'] ?? ''),
              builder: (context, followLink) {
                return TextButton(
                  onPressed: followLink,
                  child: const Text(
                    "Ver oferta",
                    style: AppTextStyles.offerLink,
                  ),
                );
              },
            ),
          ],
        ),
      ),
    );
  }
}
