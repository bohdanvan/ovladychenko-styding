package com.ovladychenko;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bvanchuhov
 */
public class HelloUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // BL
        String name = req.getParameter("name");

        // Add data to jsp attributes
        req.setAttribute("name", name);

        // Call JSP
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/hello-user.jsp");
        requestDispatcher.forward(req, resp);
    }
}
