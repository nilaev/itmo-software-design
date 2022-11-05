package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DB;
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

        if ("max".equals(command)) {
            List<Product> maxList = DB.executeSQLQuery(DB.SELECT_MAX, DB::collectProducts);
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with max price: </h1>");
            for (Product max : maxList) {
                response.getWriter().println(max.getName() + "\t" + max.getPrice() + "</br>");
            }
            response.getWriter().println("</body></html>");
        } else if ("min".equals(command)) {
            List<Product> minList = DB.executeSQLQuery(DB.SELECT_MIN, DB::collectProducts);

            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with min price: </h1>");
            for (Product min : minList) {
                response.getWriter().println(min.getName() + "\t" + min.getPrice() + "</br>");
            }
            response.getWriter().println("</body></html>");
        } else if ("sum".equals(command)) {
            Optional<Long> sum = DB.executeSQLQuery(DB.CALC_SUM, DB::extractLong);
            response.getWriter().println("<html><body>");
            response.getWriter().println("Summary price: ");
            if (sum.isPresent()) {
                response.getWriter().println(sum.get());
            }
            response.getWriter().println("</body></html>");
        } else if ("count".equals(command)) {
            Optional<Long> count = DB.executeSQLQuery(DB.CALC_COUNT, DB::extractLong);
            response.getWriter().println("<html><body>");
            response.getWriter().println("Number of products: ");
            if (count.isPresent()) {
                response.getWriter().println(count.get());
            }
            response.getWriter().println("</body></html>");
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
