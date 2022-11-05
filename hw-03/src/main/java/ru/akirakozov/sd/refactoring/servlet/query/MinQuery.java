package ru.akirakozov.sd.refactoring.servlet.query;

import ru.akirakozov.sd.refactoring.DB;
import ru.akirakozov.sd.refactoring.Product;

import java.util.List;

public class MinQuery extends Query<List<Product>> {

    public void executeQuery() {
        queryResult = DB.executeSQLQuery(DB.SELECT_MIN, DB::collectProducts);
    }

    public void initRespond() {
        re.addHeader("Product with min price: ", 1);
    }

    public void putResultInRespond() {
        for (Product min : queryResult) {
            re.addLine(min.getName() + "\t" + min.getPrice() + "</br>");
        }
    }

}