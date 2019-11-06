package ru.geekfactory.homefinance.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.geekfactory.homefinance.dao.model.CategoryTransaction;
import ru.geekfactory.homefinance.service.CategoryTransactionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {

    @Autowired
    private CategoryTransactionService categoryTransactionService;

    @GetMapping("/category")
    public String getCategories(Model model) {
        List<CategoryTransaction> categoryTransactions = categoryTransactionService.findAll();
        model.addAttribute("categories", categoryTransactions);
        return "category";
    }

    @PostMapping("/saveCategory")
    public ModelAndView createCategory(HttpServletRequest request) {
        CategoryTransaction categoryTransaction = new CategoryTransaction();
        categoryTransaction.setName(request.getParameter("name"));
        if (request.getParameter("parentCategory") != null) {
            categoryTransaction.setParent(categoryTransactionService.findById(Long.valueOf(request.getParameter("parentCategory"))).orElse(null));
        }
        categoryTransactionService.save(categoryTransaction);
        return new ModelAndView("redirect:/category");
    }

    @PostMapping("/editCategory")
    public ModelAndView editCategory(HttpServletRequest request) {
        Optional<CategoryTransaction> optionalCategory = categoryTransactionService.findById(Long.valueOf(request.getParameter("id")));
        if (optionalCategory.isPresent()) {
            CategoryTransaction categoryTransaction = optionalCategory.get();
            categoryTransaction.setName(request.getParameter("name"));
            if (!"".equals(request.getParameter("parent_" + request.getParameter("id")))) {
                categoryTransaction.setParent(categoryTransactionService.findById(Long.valueOf(request.getParameter("parent_" + request.getParameter("id")))).orElse(null));
            }
            categoryTransactionService.save(categoryTransaction);
        }
        return new ModelAndView("redirect:/category");
    }

    @PostMapping("/deleteCategory")
    public ModelAndView deleteCategory(HttpServletRequest request) {
        Optional<CategoryTransaction> optionalCategory = categoryTransactionService.findById(Long.valueOf(request.getParameter("id")));
        optionalCategory.ifPresent(categoryTransactionService::remove);
        return new ModelAndView("redirect:/category");
    }
}
