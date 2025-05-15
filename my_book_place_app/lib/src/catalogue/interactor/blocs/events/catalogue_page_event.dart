abstract class CataloguePageEvent {}

class CataloguePageLoadEvent extends CataloguePageEvent {
  final int qty;

  CataloguePageLoadEvent(this.qty);
}
