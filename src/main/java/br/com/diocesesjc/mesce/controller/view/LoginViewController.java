package br.com.diocesesjc.mesce.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public String postLogin() {
        return "redirect:home";
    }
}
