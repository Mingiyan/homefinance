package ru.geekfactory.homefinance.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import ru.geekfactory.homefinance.dao.model.Currency;
import ru.geekfactory.homefinance.model.ConvertedCurrency;
import ru.geekfactory.homefinance.service.CurrencyService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/currency")
    public String getCurrencies(Model model) {
        List<ConvertedCurrency> currencies = currencyService.findAllConverted();
        model.addAttribute("currencies", currencies);
        return "currency";
    }

    @PostMapping("/saveCurrency")
    public ModelAndView createCurrency(HttpServletRequest request) {
        String name = request.getParameter("name");
        ConvertedCurrency currency = new ConvertedCurrency();
        currency.setName(name);
        currencyService.saveConverted(currency);
        return new ModelAndView("redirect:/currency");
    }

    @PostMapping("/editCurrency")
    public ModelAndView editCurrency(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        ConvertedCurrency currency = currencyService.findByIdConverted(id);
        currency.setName(name);
        currencyService.updateConverted(currency);
        return new ModelAndView("redirect:/currency");
    }

    @PostMapping("/deleteCurrency")
    public ModelAndView deleteCurrency(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        ConvertedCurrency currency = currencyService.findByIdConverted(id);
        currencyService.removeConverted(currency);
        return new ModelAndView("redirect:/currency");
    }

    @PostMapping(value = "/saveCurrencyJson", produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveAccountJson(@RequestBody Currency currency) {
        currencyService.save(currency);
        return "currency_save_success";
    }
}
