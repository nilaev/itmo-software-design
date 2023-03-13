package app.controller;

import app.client.StocksClient;
import app.model.Stock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.client.StocksClientImpl;
import app.users.UserDaoInMemory;

import java.util.List;

@RestController
public class UserController {
    private final StocksClient client = new StocksClientImpl("http://127.0.0.1", 8084);
    private final UserDaoInMemory dao = new UserDaoInMemory(client);

    @RequestMapping("/add_user")
    public int addUser(String name) {
        return dao.addUser(name);
    }

    @RequestMapping("/add_money")
    public ResponseEntity<String> addMoney(int id, double money) {
        if (dao.changeMoney(id, money)) {
            return ResponseEntity.ok("");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping("/get_user_stocks")
    public List<Stock> getUserStocks(int id) {
        return dao.getUserStocks(id);
    }

    @RequestMapping("/get_user_money")
    public ResponseEntity<Double> getUserMoney(int id) {
        Double userMoney = dao.getUserMoney(id);
        if (userMoney != null) {
            return ResponseEntity.ok(userMoney);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping("/buy_stock")
    public ResponseEntity<String> buyStock(int userId, int companyId, int count) {
        if (dao.buyStock(userId, companyId, count)) {
            return ResponseEntity.ok("");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping("/sold_stock")
    public ResponseEntity<String> soldStock(int userId, int companyId, int count) {
        if (dao.soldStock(userId, companyId, count)) {
            return ResponseEntity.ok("");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
