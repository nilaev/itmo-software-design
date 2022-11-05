package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DB;
import ru.akirakozov.sd.refactoring.HttpResponse;
import ru.akirakozov.sd.refactoring.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        HttpResponse re = new HttpResponse();

        if ("max".equals(command)) {
            List<Product> maxList = DB.executeSQLQuery(DB.SELECT_MAX, DB::collectProducts);

            re.addHeader("Product with max price: ", 1);
            for (Product max : maxList) {
                re.addLine(max.getName() + "\t" + max.getPrice() + "</br>");
            }
        } else if ("min".equals(command)) {
            List<Product> minList = DB.executeSQLQuery(DB.SELECT_MIN, DB::collectProducts);

            re.addHeader("Product with min price: ", 1);
            for (Product min : minList) {
                re.addLine(min.getName() + "\t" + min.getPrice() + "</br>");
            }
        } else if ("sum".equals(command)) {
            Optional<Long> sum = DB.executeSQLQuery(DB.CALC_SUM, DB::extractLong);
            re.addLine("Summary price: ");
            sum.ifPresent(re::addLine);
        } else if ("count".equals(command)) {
            Optional<Long> count = DB.executeSQLQuery(DB.CALC_COUNT, DB::extractLong);
            re.addLine("Number of products: ");
            count.ifPresent(re::addLine);
        } else {
            re.addLine("Unknown command: " + command);
        }

        re.commit(response);
    }

}
