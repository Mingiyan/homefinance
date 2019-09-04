package ru.geekfactory.homefinance.web.servlet;

import ru.geekfactory.homefinance.dao.model.AccountModel;
import ru.geekfactory.homefinance.dao.model.AccountType;
import ru.geekfactory.homefinance.service.AccountService;
import ru.geekfactory.homefinance.service.CurrencyService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "accountServlet", urlPatterns = "/account")
public class AccountServlet extends HttpServlet {

    private AccountService accountService = new AccountService();
    private CurrencyService currencyService = new CurrencyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("accounts", accountService.findAll());
        req.setAttribute("currencies", currencyService.findAll());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/jsp/account.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AccountModel account = new AccountModel();
        account.setName(req.getParameter("name"));
        account.setAmount(BigDecimal.valueOf(Double.valueOf(req.getParameter("amount"))));
        account.setAccountType(AccountType.valueOf(req.getParameter("accountType")));
        account.setCurrency(currencyService.findById(Long.valueOf(req.getParameter("currency"))).orElse(null));
        accountService.save(account);
        resp.sendRedirect("/account");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AccountModel account = new AccountModel();
        account.setId(Long.valueOf(req.getParameter("id")));
        account.setName(req.getParameter("name"));
        account.setAmount(BigDecimal.valueOf(Double.valueOf(req.getParameter("amount"))));
        account.setAccountType(AccountType.valueOf(req.getParameter("accountType")));
        account.setCurrency(currencyService.findById(Long.valueOf(req.getParameter("currency"))).orElse(null));
        accountService.update(account);
        resp.sendRedirect("/account");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AccountModel account = accountService.findById(Long.valueOf(req.getParameter("id"))).orElse(null);
        accountService.remove(account);
        resp.sendRedirect("/account");
    }
}
