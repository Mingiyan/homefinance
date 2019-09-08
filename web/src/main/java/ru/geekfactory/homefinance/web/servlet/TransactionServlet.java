package ru.geekfactory.homefinance.web.servlet;

import ru.geekfactory.homefinance.dao.model.CategoryTransactionModel;
import ru.geekfactory.homefinance.dao.model.TransactionModel;
import ru.geekfactory.homefinance.service.AccountService;
import ru.geekfactory.homefinance.service.CategoryTransactionService;
import ru.geekfactory.homefinance.service.TransactionService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "transactionServlet", urlPatterns = "/transaction")
public class TransactionServlet extends HttpServlet {

    private TransactionService transactionService = new TransactionService();
    private AccountService accountService = new AccountService();
    private CategoryTransactionService categoryTransactionService = new CategoryTransactionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TransactionModel> list = new ArrayList<>();
        if (req.getParameter("search") == null ) {
            list = transactionService.findAll();
        } else {
            list.add(transactionService.findById(Long.valueOf(req.getParameter("search"))).orElse(null));
        }
        req.setAttribute("transactions", list);
        req.setAttribute("accounts", accountService.findAll());
        req.setAttribute("categories", categoryTransactionService.findAll());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/jsp/transaction.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setName(req.getParameter("name"));
        transactionModel.setDateTime(LocalDateTime.parse(req.getParameter("dateTime")));
        transactionModel.setAccount(accountService.findById(Long.valueOf(req.getParameter("account"))).orElse(null));
        Collection<CategoryTransactionModel> collection = new ArrayList<>();
        for (int i = 2; i <= Integer.valueOf(req.getParameter("category_counter")); i++) {
            collection.add(categoryTransactionService.findById(Long.valueOf(req.getParameter("category_" + i))).orElse(null));
        }
        transactionModel.setCategoryTransaction(collection);
        transactionService.save(transactionModel);
        resp.sendRedirect("/transaction");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setId(Long.valueOf(req.getParameter("id")));
        transactionModel.setName(req.getParameter("name"));
        transactionModel.setDateTime(LocalDateTime.parse(req.getParameter("dateTime")));
        transactionModel.setAccount(accountService.findById(Long.valueOf(req.getParameter("account"))).orElse(null));
        Collection<CategoryTransactionModel> collection = new ArrayList<>();
        for (String s : req.getParameterValues("categories")) {
            collection.add(categoryTransactionService.findById(Long.valueOf(s)).orElse(null));
        }
        transactionModel.setCategoryTransaction(collection);
        transactionService.update(transactionModel);
        resp.sendRedirect("/transaction");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TransactionModel transactionModel = transactionService.findById(Long.valueOf(req.getParameter("id"))).orElse(null);
        transactionService.remove(transactionModel);
        resp.sendRedirect("/transaction");
    }
}
