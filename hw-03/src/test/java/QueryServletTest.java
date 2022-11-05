import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QueryServletTest {
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response;
    private StringWriter responseContent;

    private static class QueryServletOpen extends QueryServlet {
        @Override
        public void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
            super.doGet(request, response);
        }
    }

    @BeforeEach
    void createResponseMock() throws IOException {
        response = mock(HttpServletResponse.class);

        responseContent = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseContent));
    }

    @Test
    void min_request() throws IOException, SQLException {
        when(request.getParameter("command")).thenReturn("min");

        new QueryServletOpen().doGet(request, response);

        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
        String header = "<h1>Product with min price: </h1>\n";

        assertEquals(
                UtilsDB.executeQueryAndFormatForHTML(sql, header, UtilsDB.appendNameAndPrice),
                responseContent.toString()
        );
    }

    @Test
    void max_request() throws IOException, SQLException {
        when(request.getParameter("command")).thenReturn("max");

        new QueryServletOpen().doGet(request, response);

        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
        String header = "<h1>Product with max price: </h1>\n";

        assertEquals(
                UtilsDB.executeQueryAndFormatForHTML(sql, header, UtilsDB.appendNameAndPrice),
                responseContent.toString()
        );
    }

    @Test
    void sum_request() throws IOException, SQLException {
        when(request.getParameter("command")).thenReturn("sum");

        new QueryServletOpen().doGet(request, response);

        String sql = "SELECT SUM(price) as value FROM PRODUCT";
        String header = "Summary price: \n";

        assertEquals(
                UtilsDB.executeQueryAndFormatForHTML(sql, header, UtilsDB.appendInt),
                responseContent.toString()
        );
    }

    @Test
    void count_request() throws IOException, SQLException {
        when(request.getParameter("command")).thenReturn("count");

        new QueryServletOpen().doGet(request, response);

        String sql = "SELECT COUNT(*) as value FROM PRODUCT";
        String header = "Number of products: \n";

        assertEquals(
                UtilsDB.executeQueryAndFormatForHTML(sql, header, UtilsDB.appendInt),
                responseContent.toString()
        );
    }

    @Test
    void unknown_request() throws IOException, SQLException {
        when(request.getParameter("command")).thenReturn("do");

        new QueryServletOpen().doGet(request, response);

        assertEquals(
                "Unknown command: do\n",
                responseContent.toString()
        );
    }
}
