package br.com.diocesesjc.mesce.controller.view;

import br.com.diocesesjc.mesce.service.InitialDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginViewController {

    private final InitialDataService initialDataService;

    public LoginViewController(InitialDataService initialDataService) {
        this.initialDataService = initialDataService;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        initialDataService.createInitialData();
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView postLogin() {
        return new ModelAndView("home");
    }
}
