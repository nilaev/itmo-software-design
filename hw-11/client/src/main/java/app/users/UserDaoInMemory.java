package app.users;

import app.client.StocksClient;
import app.model.Stock;
import app.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoInMemory implements UserDao {
    private final List<User> users = new ArrayList<>();
    private final StocksClient client;

    public UserDaoInMemory(StocksClient client) {
        this.client = client;
    }

    @Override
    public int addUser(String name) {
        int id = users.size();
        users.add(new User(id, name, 0, new HashMap<>()));

        return id;
    }

    @Override
    public boolean changeMoney(int id, double money) {
        if (id < users.size()) {
            return users.get(id).changeMoney(money);
        }

        return false;
    }

    @Override
    public List<Stock> getUserStocks(int userId) {
        List<Stock> stocks = new ArrayList<>();

        if (userId < users.size()) {
            User user = users.get(userId);
            for (Map.Entry<Integer, Integer> stock : user.getStocks().entrySet()) {
                int companyId = stock.getKey();
                int countStocks = stock.getValue();
                stocks.add(new Stock(companyId, client.getStockPrice(companyId), countStocks));
            }
        }

        return stocks;
    }

    @Override
    public Double getUserMoney(int userId) {
        Double userMoney = null;

        if (userId < users.size()) {
            List<Stock> stocks = getUserStocks(userId);

            userMoney = users.get(userId).getMoney();
            for (Stock s : stocks) {
                userMoney += s.getNumber() * s.getPrice();
            }
        }

        return userMoney;
    }

    @Override
    public boolean buyStock(int userId, int companyId, int number) {
        if (userId >= users.size()) {
            return false;
        }

        User user = users.get(userId);
        double totalCost = client.getStockPrice(companyId) * number;
        if (user.getMoney() - totalCost < 0) {
            return false;
        }

        boolean bought = client.buyStocks(companyId, number);

        if (bought) {
            user.getStocks().put(companyId, user.getStocks().getOrDefault(companyId, 0) + number);
            return user.changeMoney(-totalCost);
        } else {
            return false;
        }
    }

    @Override
    public boolean soldStock(int userId, int companyId, int number) {
        if (userId >= users.size()) {
            return false;
        }

        User user = users.get(userId);
        double totalCost = client.getStockPrice(companyId);
        if (user.getStocks().getOrDefault(companyId, 0) < number) {
            return false;
        }

        boolean sold = client.sellStocks(companyId, number);

        if (sold) {
            user.getStocks().put(companyId, user.getStocks().getOrDefault(companyId, 0) - number);
            return user.changeMoney(totalCost);
        } else {
            return false;
        }
    }
}
