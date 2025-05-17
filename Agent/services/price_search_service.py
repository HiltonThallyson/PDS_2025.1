class PriceSearchService:
    def __init__(self, price_repository):
        self.price_repository = price_repository

    def search_prices(self, query):
        return self.price_repository.find_prices(query)