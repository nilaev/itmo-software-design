package app.users;


import app.model.Stock;

import java.util.List;

public interface UserDao {
    int addUser(String name);

    boolean changeMoney(int userId, double money);

    List<Stock> getUserStocks(int userId);

    Double getUserMoney(int userId);

    boolean buyStock(int userId, int companyId, int number);

    boolean soldStock(int userId, int companyId, int number);
}
