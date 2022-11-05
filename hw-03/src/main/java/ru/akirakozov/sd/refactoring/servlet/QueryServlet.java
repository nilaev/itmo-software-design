package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.HttpResponse;
import ru.akirakozov.sd.refactoring.servlet.query.Query;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        Query<?> query = Query.init(command);
        HttpResponse re = query.execute();
        re.commit(response);
    }

}