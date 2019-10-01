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
import ru.geekfactory.homefinance.dao.model.Currency;
import ru.geekfactory.homefinance.service.CurrencyService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/currency")
    public String getCurrencies(Model model) {
        List<Currency> currencies = currencyService.findAll();
        model.addAttribute("currencies", currencies);
        return "currency";
    }

    @PostMapping("/saveCurrency")
    public ModelAndView createCurrency(HttpServletRequest request) {
        String name = request.getParameter("name");
        Currency currency = new Currency();
        currency.setName(name);
        currencyService.save(currency);
        return new ModelAndView("redirect:/currency");
    }

    @PostMapping("/editCurrency")
    public ModelAndView editCurrency(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        Optional<Currency> optionalCurrency = currencyService.findById(id);
        if (optionalCurrency.isPresent()) {
            Currency currency = optionalCurrency.get();
            currency.setName(name);
            currencyService.update(currency);
        }
        return new ModelAndView("redirect:/currency");
    }

    @PostMapping("/deleteCurrency")
    public ModelAndView deleteCurrency(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        Optional<Currency> optionalCurrency = currencyService.findById(id);
        optionalCurrency.ifPresent(currencyService::remove);
        return new ModelAndView("redirect:/currency");
    }

    @PostMapping(value = "/saveCurrencyJson", produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveAccountJson(@RequestBody Currency currency) {
        currencyService.save(currency);
        return "currency_save_success";
    }
}
