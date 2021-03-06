package ru.geekfactory.homefinance.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.geekfactory.homefinance.dao.model.Account;
import ru.geekfactory.homefinance.dao.model.CategoryTransaction;
import ru.geekfactory.homefinance.dao.model.Transaction;
import ru.geekfactory.homefinance.service.AccountService;
import ru.geekfactory.homefinance.service.CategoryTransactionService;
import ru.geekfactory.homefinance.service.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class TransactionController {

    private TransactionService transactionService;
    private AccountService accountService;
    private CategoryTransactionService categoryTransactionService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService, CategoryTransactionService categoryTransactionService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.categoryTransactionService = categoryTransactionService;
    }

    @GetMapping("/transaction")
    public String getTransactions(Model model) {
        List<Transaction> transactions = transactionService.findAll();
        List<Account> accounts = accountService.findAll();
        List<CategoryTransaction> categories = categoryTransactionService.findAll();
        model.addAttribute("transactions", transactions);
        model.addAttribute("accounts", accounts);
        model.addAttribute("categories", categories);
        return "transaction";
    }

    @PostMapping("/saveTransaction")
    public ModelAndView createTransaction(HttpServletRequest request) {
        Transaction transaction = new Transaction();
        transaction.setName(request.getParameter("name"));
        transaction.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
        transaction.setAccount(accountService.findById(Long.valueOf(request.getParameter("account"))).orElse(null));
        transaction.setAmount(BigDecimal.valueOf(Double.valueOf(request.getParameter("amount"))));
        Collection<CategoryTransaction> collection = new ArrayList<>();
        if (request.getParameterValues("categories") != null) {
            for (String s : request.getParameterValues("categories")) {
                collection.add(categoryTransactionService.findById(Long.valueOf(s)).orElse(null));
            }
        }
        transaction.setCategoryTransactions(collection);
        transactionService.save(transaction);
        return new ModelAndView("redirect:/transaction");
    }

    @PostMapping("/editTransaction")
    public ModelAndView editTransaction(HttpServletRequest request) {
        Optional<Transaction> optionalTransaction = transactionService.findById(Long.valueOf(request.getParameter("id")));
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            transaction.setName(request.getParameter("name"));
            transaction.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
            transaction.setAmount(BigDecimal.valueOf(Double.valueOf(request.getParameter("amount"))));
            transaction.setAccount(accountService.findById(Long.valueOf(request.getParameter("account"))).orElse(null));
            Collection<CategoryTransaction> collection = new ArrayList<>();
            if (request.getParameterValues("categories") != null) {
                for (String s : request.getParameterValues("categories")) {
                    collection.add(categoryTransactionService.findById(Long.valueOf(s)).orElse(null));
                }
            }
            transaction.setCategoryTransactions(collection);
            transactionService.save(transaction);
        }
        return new ModelAndView("redirect:/transaction");
    }

    @PostMapping("deleteTransaction")
    public ModelAndView delelteTransaction(HttpServletRequest request) {
        Optional<Transaction> optionalTransaction = transactionService.findById(Long.valueOf(request.getParameter("id")));
        optionalTransaction.ifPresent(transactionService::remove);
        return new ModelAndView("redirect:/transaction");
    }
}
