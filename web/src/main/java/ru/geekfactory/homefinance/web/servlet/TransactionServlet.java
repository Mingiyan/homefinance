package ru.geekfactory.homefinance.web.servlet;

import ru.geekfactory.homefinance.dao.model.TransactionModel;
import ru.geekfactory.homefinance.service.TransactionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        TransactionService transactionService = new TransactionService();
        List<TransactionModel> list = transactionService.findAll();
        for (TransactionModel transaction : list) {
            resp.getOutputStream().write((transaction.toString() + "\n").getBytes());
        }
    }
}
