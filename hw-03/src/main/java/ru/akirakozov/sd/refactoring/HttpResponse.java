package ru.akirakozov.sd.refactoring;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HttpResponse {
    private final List<String> lines;

    public HttpResponse() {
        lines = new ArrayList<>();
    }

    public HttpResponse(String firstLine) {
        lines = new ArrayList<>();
        lines.add(firstLine);

    }

    public void addLine(String line) {
        lines.add(line);
    }

    public void addLine(long line) {
        lines.add(Long.toString(line));
    }

    public void addHeader(String line, int level) {
        lines.add(String.format("<h%d>%s</h%s>", level, line, level));
    }


    public void commit(HttpServletResponse response) {
        try {
            boolean needed_head = true;
            for (String line : lines) {
                if (Objects.equals(line, "OK")) {
                    needed_head = false;
                }
            }
            if (needed_head) {
                response.getWriter().println("<html><body>");
            }
            for (String line : lines) {
                response.getWriter().println(line);
            }
            if (needed_head) {
                response.getWriter().println("</body></html>");
            }
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            lines.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}