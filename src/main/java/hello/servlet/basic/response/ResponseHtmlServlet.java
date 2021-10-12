package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * HTTP 응답데이터 HTML
 * http://localhost:8080/reponse-html
 */
@WebServlet(name = "responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. content-type 설정
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        // 2. html
        PrintWriter printWriter = resp.getWriter();
        printWriter.println("<html>");
        printWriter.println("<body");
        printWriter.println("<div>안녕?</div>");
        printWriter.println("</body");
        printWriter.println("</html>");
    }
}
