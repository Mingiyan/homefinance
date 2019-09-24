package ru.geekfactory.homefinance.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.geekfactory.homefinance.dao.model.Currency;
import ru.geekfactory.homefinance.service.CurrencyService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
}
