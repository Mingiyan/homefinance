package ru.geekfactory.homefinance.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.geekfactory.homefinance.dao.model.User;
import ru.geekfactory.homefinance.service.UserService;
import ru.geekfactory.homefinance.web.handler.Encryption;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Optional<User> optionalUser = userService.findByLogin(login);
        if (!optionalUser.isPresent()) { return new ModelAndView("redirect:/registration");}
        User person = optionalUser.get();
        if (Encryption.matches(password, person.getPassword())) {
            HttpSession session = req.getSession();
            session.setAttribute("login", login);
            session.setAttribute("password", password);
            return new ModelAndView("redirect:/index");
        } else {
            return new ModelAndView("redirect:/login");
        }
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView registration(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isPresent()) { return new ModelAndView("redirect:/loginFailed");}
        User newUser = new User();
        userService.save(newUser);
        HttpSession session = req.getSession();
        session.setAttribute("login" , login);
        session.setAttribute("password", password);
        return new ModelAndView("redirect:/login");
    }

}
