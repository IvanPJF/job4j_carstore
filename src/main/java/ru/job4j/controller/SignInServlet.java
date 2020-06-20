package ru.job4j.controller;

import ru.job4j.model.RegAdvertiser;
import ru.job4j.service.Service;
import ru.job4j.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class SignInServlet extends HttpServlet {

    private static final Service SERVICE = ValidateService.getInstance();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        req.getRequestDispatcher("/WEB-INF/html/signin.html").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try (final PrintWriter writer = resp.getWriter()) {
            if (!SERVICE.isCredential(new RegAdvertiser(login, password))) {
                writer.print("incorrect");
                return;
            }
            HttpSession session = req.getSession();
            session.setAttribute("advertiser", SERVICE.findAdvertiserByLogin(new RegAdvertiser(login)));
        }
    }
}
