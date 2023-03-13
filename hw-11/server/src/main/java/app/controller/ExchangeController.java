package app.controller;

import app.dao.ExchangeDaoInMemory;
import app.model.Stock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeController {
    private final ExchangeDaoInMemory dao = new ExchangeDaoInMemory();

    @RequestMapping("/add_company")
    public int addCompany(String name) {
        return dao.addCompanyStocks(name);
    }

    @RequestMapping("/add_stocks")
    public ResponseEntity<Integer> addStocks(int id, int number) {
        if (dao.addStocks(id, number)) {
            return ResponseEntity.ok(number);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping("/change_stock_cost")
    public ResponseEntity<Double> changeStock(int id, double delta) {
        if (dao.changeStockPrice(id, delta)) {
            return ResponseEntity.ok(delta);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping("/get_stock_price")
    public ResponseEntity<Double> getStockPrice(int id) {
        Stock stock = dao.getStock(id);
        if (stock != null) {
            return ResponseEntity.ok(stock.getPrice());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping("/get_stock_count")
    public ResponseEntity<Integer> getStockCount(int id) {
        Stock stock = dao.getStock(id);
        if (stock != null) {
            return ResponseEntity.ok(stock.getNumber());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping("/buy_stock")
    public ResponseEntity<Integer> buyStock(int id, int number) {
        if (dao.buyStock(id, number)) {
            return ResponseEntity.ok(number);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @RequestMapping("/sell_stock")
    public HttpEntity<Double> sellStock(int id, int number) {
        double total = dao.sellStock(id, number);
        if (total != 0) {
            return ResponseEntity.ok(total);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
