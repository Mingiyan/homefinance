package ru.geekfactory.homefinance.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import ru.geekfactory.homefinance.dao.model.Account;
import ru.geekfactory.homefinance.dao.model.AccountType;
import ru.geekfactory.homefinance.dao.model.Currency;
import ru.geekfactory.homefinance.service.AccountService;
import ru.geekfactory.homefinance.service.CurrencyService;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/account")
    public String getAccounts(Model model) {
        List<Account> accounts = accountService.findAll();
        List<Currency> currencies = currencyService.findAll();
        model.addAttribute("currencies", currencies);
        model.addAttribute("accounts", accounts);
        model.addAttribute("accountTypes", AccountType.values());
        return "account";
    }

    @PostMapping("/saveAccount")
    public ModelAndView createAccount(HttpServletRequest request) {
        Account account = new Account();
        account.setName(request.getParameter("name"));
        account.setAmount(BigDecimal.valueOf(Double.valueOf(request.getParameter("amount"))));
        account.setAccountType(AccountType.valueOf(request.getParameter("accountType")));
        account.setCurrency(currencyService.findById(Long.valueOf(request.getParameter("currency"))).orElse(null));
        accountService.save(account);
        return new ModelAndView("redirect:/account");
    }

    @PostMapping("/editAccount")
    public ModelAndView editAccount(HttpServletRequest request) {
        Optional<Account> optionalAccount = accountService.findById(Long.valueOf(request.getParameter("id")));
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setName(request.getParameter("name"));
            account.setAmount(BigDecimal.valueOf(Double.valueOf(request.getParameter("amount"))));
            account.setAccountType(AccountType.valueOf(request.getParameter("accountType")));
            account.setCurrency(currencyService.findById(Long.valueOf(request.getParameter("currency"))).orElse(null));
            accountService.save(account);
        }
        return new ModelAndView("redirect:/account");
    }

    @PostMapping("/deleteAccount")
    public ModelAndView deleteAccount(HttpServletRequest request) {
        Optional<Account> optionalAccount = accountService.findById(Long.valueOf(request.getParameter("id")));
        optionalAccount.ifPresent(accountService::remove);
        return new ModelAndView("redirect:/account");
    }
}
