package br.com.diocesesjc.mesce.controller.view;

import br.com.diocesesjc.mesce.service.InitialDataService;
import br.com.diocesesjc.mesce.service.ViewGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeViewController extends InternalViewController {

    private final InitialDataService initialDataService;

    public HomeViewController(ViewGroupService viewGroupService, InitialDataService initialDataService) {
        super(viewGroupService);
        this.initialDataService = initialDataService;
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = super.render("home");
        initialDataService.createInitialData();
        return modelAndView;
    }
}
