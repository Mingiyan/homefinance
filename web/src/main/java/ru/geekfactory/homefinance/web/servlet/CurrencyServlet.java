package ru.geekfactory.homefinance.web.servlet;

import ru.geekfactory.homefinance.dao.model.CurrencyModel;
import ru.geekfactory.homefinance.service.CurrencyService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/currency")
public class CurrencyServlet extends HttpServlet {

    private CurrencyService currencyService = new CurrencyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("currencies", currencyService.findAll());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/jsp/currency.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        CurrencyModel currencyModel = new CurrencyModel();
        currencyModel.setName(req.getParameter("name"));
        currencyService.save(currencyModel);
        resp.sendRedirect("/currency");
    }
}
