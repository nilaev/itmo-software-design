import java.sql.*;

public class UtilsDB {
    public static String executeQueryAndFormatForHTML(String sql, String header, ResultSetHandler rsHandler) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            StringBuilder sb = new StringBuilder();
            sb.append("<html><body>\n");
            sb.append(header);

            rsHandler.handle(sb, rs);

            sb.append("</body></html>\n");

            stmt.close();

            return sb.toString();
        }
    }

    @FunctionalInterface
    private interface ResultSetHandler {
        void handle(StringBuilder sb, ResultSet rs) throws SQLException;
    }

    protected static ResultSetHandler appendNameAndPrice = (StringBuilder sb, ResultSet rs) -> {
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            sb.append(name).append("\t").append(price).append("</br>\n");
        }
    };

    static ResultSetHandler appendInt = (StringBuilder sb, ResultSet rs) -> {
        while (rs.next()) {
            sb.append(rs.getInt("value")).append('\n');
        }
    };
}
