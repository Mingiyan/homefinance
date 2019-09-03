package ru.geekfactory.homefinance.web.servlet;

import ru.geekfactory.homefinance.service.TransactionService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "transactionServlet", urlPatterns = "/transaction")
public class TransactionServlet extends HttpServlet {

    private TransactionService transactionService = new TransactionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("transactions", transactionService.findAll());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/jsp/transaction.jsp");
        requestDispatcher.forward(req, resp);
    }
}
