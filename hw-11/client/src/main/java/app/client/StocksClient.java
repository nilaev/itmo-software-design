package app.client;

public interface StocksClient {
    boolean buyStocks(int companyId, int number);

    boolean sellStocks(int companyId, int number);

    Double getStockPrice(int companyId);

    Integer getStockCount(int companyId);

    Integer addCompany(String name);

    boolean changeCost(int id, double delta);

    boolean addStocks(int id, int number);
}
