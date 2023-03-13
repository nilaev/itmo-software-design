package app.dao;

import app.model.Stock;

public interface ExchangeDao {
    int addCompanyStocks(String name);

    boolean addStocks(int companyId, int number);

    boolean changeStockPrice(int companyId, double delta);

    Stock getStock(int companyId);

    boolean buyStock(int companyId, int number);

    double sellStock(int companyId, int number);
}
