package ru.geekfactory.homefinance.web.servlet;

import ru.geekfactory.homefinance.service.AccountService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("accounts", accountService.findAll());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/jsp/account.jsp");
        requestDispatcher.forward(req, resp);
    }
}
