package ru.geekfactory.homefinance.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.geekfactory.homefinance.dao.model.User;
import ru.geekfactory.homefinance.dao.model.UserRole;
import ru.geekfactory.homefinance.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView registration(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isPresent()) { return new ModelAndView("redirect:/");}
        User newUser = new User();
        newUser.setLogin(login);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        newUser.setPassword(encoder.encode(password));
        newUser.setEnabled(true);
        newUser.setRole(UserRole.ROLE_USER);
        userService.save(newUser);
        return new ModelAndView("redirect:/login");
    }

}
