package ru.geekfactory.homefinance.web.servlet;

import ru.geekfactory.homefinance.service.CategoryTransactionService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

    private CategoryTransactionService categoryTransactionService = new CategoryTransactionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("categories", categoryTransactionService.findAll());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/jsp/category.jsp");
        requestDispatcher.forward(req, resp);
    }
}
