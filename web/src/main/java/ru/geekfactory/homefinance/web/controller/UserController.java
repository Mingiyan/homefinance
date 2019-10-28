package ru.geekfactory.homefinance.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.geekfactory.homefinance.dao.model.User;
import ru.geekfactory.homefinance.dao.model.UserRole;
import ru.geekfactory.homefinance.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/saveUser")
    public ModelAndView createCurrency(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Boolean enabled = false;
        if ("enabled".equals(request.getParameter("enabled"))) {
            enabled = true;
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(encoder.encode(password));
        user.setEnabled(enabled);
        user.setRole(UserRole.valueOf(request.getParameter("role")));
        userService.save(user);
        return new ModelAndView("redirect:/users");
    }

    @PostMapping("/editUser")
    public ModelAndView editCurrency(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setLogin(login);
            user.setPassword(encoder.encode(password));
            Boolean enabled = false;
            if ("enabled".equals(request.getParameter("enabled"))) {
                enabled = true;
            }
            user.setEnabled(enabled);
            user.setRole(UserRole.valueOf(request.getParameter("role")));
            userService.update(user);
        }
        return new ModelAndView("redirect:/users");
    }

    @PostMapping("/deleteUser")
    public ModelAndView deleteCurrency(HttpServletRequest request) {
        Optional<User> optionalUser = userService.findById(Long.valueOf(request.getParameter("id")));
        optionalUser.ifPresent(userService::remove);
        return new ModelAndView("redirect:/users");
    }
}
