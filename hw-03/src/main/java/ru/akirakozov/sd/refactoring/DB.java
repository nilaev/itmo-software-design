package ru.akirakozov.sd.refactoring;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class DB {

    private static final String DB_NAME = "jdbc:sqlite:test.db";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS PRODUCT " +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL) ";

    public static final String SELECT_MIN = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
    public static final String SELECT_MAX = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
    public static final String CALC_SUM = "SELECT SUM(price) as value FROM PRODUCT";
    public static final String CALC_COUNT = "SELECT COUNT(*) FROM PRODUCT";
    public static final String GET_PRODUCTS = "SELECT * FROM PRODUCT";

    public static String addProduct(Product p) {
        return "INSERT INTO PRODUCT (NAME, PRICE) " +
                "VALUES (\"" + p.getName() + "\"," + p.getPrice() + ")";
    }



    public static void executeSQLUpdate(String sql) {
        try (Connection c = DriverManager.getConnection(DB_NAME)) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static <T> T executeSQLQuery(String sql, Function<ResultSet, T> resultCollector) {
        T result = null;
        try (Connection c = DriverManager.getConnection(DB_NAME)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            result = resultCollector.apply(rs);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }



    public static Optional<Long> extractLong(ResultSet rs) {
        try {
            if (rs.next()) {
                return Optional.of(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static List<Product> collectProducts(ResultSet rs) {
        List<Product> products = new ArrayList<>();
        try {
            while (rs.next()) {
                products.add(new Product(rs.getString("name"), rs.getLong("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

}
