package Servlets;

import stateless.Points;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/receive")
public class RecieveServlet extends HttpServlet {
    @EJB
    private Points points;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        try {
            String username = req.getParameter("username");
            System.out.println("user: " + username);
            String answer = points.getPoints(username);
            System.out.println(answer);
            resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3355");
            resp.addHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,DELETE,PUT");
            resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
            resp.addHeader("Origin", "http://localhost:3355");
            System.out.println("RecieveServlet finished");
            resp.getWriter().write(answer);
        } catch (NumberFormatException | NullPointerException exception) {
            exception.printStackTrace();
        }
    }
}
