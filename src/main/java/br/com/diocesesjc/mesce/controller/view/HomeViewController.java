package br.com.diocesesjc.mesce.controller.view;

import br.com.diocesesjc.mesce.service.ViewGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeViewController extends InternalViewController {

    public HomeViewController(ViewGroupService viewGroupService) {
        super(viewGroupService);
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = super.render("home");
        return modelAndView;
    }
}
