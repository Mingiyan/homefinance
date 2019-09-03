package ru.geekfactory.homefinance.web.servlet;

import ru.geekfactory.homefinance.dao.model.CategoryTransactionModel;
import ru.geekfactory.homefinance.service.CategoryTransactionService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "categoryServlet", urlPatterns = "/category")
public class CategoryServlet extends HttpServlet {

    private CategoryTransactionService categoryTransactionService = new CategoryTransactionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categories", categoryTransactionService.findAll());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/jsp/category.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
        categoryTransactionModel.setName(req.getParameter("name"));
        if (req.getParameter("parentCategory") != null) {
            categoryTransactionModel.setParentCategory(categoryTransactionService.findById(Long.valueOf(req.getParameter("parentCategory"))).orElse(null));
        }
        categoryTransactionService.save(categoryTransactionModel);
        resp.sendRedirect("/category");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
        categoryTransactionModel.setId(Long.valueOf(req.getParameter("id")));
        categoryTransactionModel.setName(req.getParameter("name"));
        if (req.getParameter("parent_" + req.getParameter("id")) != null) {
            categoryTransactionModel.setParentCategory(categoryTransactionService.findById(Long.valueOf(req.getParameter("parent_" + req.getParameter("id")))).orElse(null));
        }
        categoryTransactionService.update(categoryTransactionModel);
        resp.sendRedirect("/category");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CategoryTransactionModel categoryTransactionModel = categoryTransactionService.findById(Long.valueOf(req.getParameter("id"))).orElse(null);
        categoryTransactionService.remove(categoryTransactionModel);
        resp.sendRedirect("/category");
    }
}
