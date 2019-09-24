package ru.geekfactory.homefinance.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.geekfactory.homefinance.dao.model.CategoryTransaction;
import ru.geekfactory.homefinance.service.CategoryTransactionService;

import java.util.List;

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

}
