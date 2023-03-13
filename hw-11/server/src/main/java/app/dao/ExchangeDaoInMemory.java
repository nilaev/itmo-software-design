package app.dao;

import app.model.Stock;

import java.util.ArrayList;
import java.util.List;

public class ExchangeDaoInMemory implements ExchangeDao {
    private final List<Stock> stocks = new ArrayList<>();

    @Override
    public int addCompanyStocks(String name) {
        int id = stocks.size();
        stocks.add(new Stock(id, name, 0, 0));

        return id;
    }

    @Override
    public boolean addStocks(int companyId, int number) {
        if (companyId >= stocks.size() || number <= 0) {
            return false;
        }

        return stocks.get(companyId).changeNumber(number);
    }

    @Override
    public boolean changeStockPrice(int companyId, double delta) {
        if (companyId >= stocks.size()) {
            return false;
        }

        return stocks.get(companyId).changePrice(delta);
    }

    @Override
    public Stock getStock(int companyId) {
        if (companyId >= stocks.size()) {
            return null;
        }

        return stocks.get(companyId);
    }

    @Override
    public boolean buyStock(int companyId, int number) {
        if (companyId >= stocks.size() || number <= 0) {
            return false;
        }

        return stocks.get(companyId).changeNumber(number);
    }

    @Override
    public double sellStock(int companyId, int number) {
        if (companyId >= stocks.size() || number <= 0) {
            return 0;
        }

        Stock stock = stocks.get(companyId);
        stock.changeNumber(number);

        return stock.getPrice() * number;
    }
}
