package br.com.diocesesjc.mesce.controller.view;

import br.com.diocesesjc.mesce.service.ViewGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SetoresViewController extends InternalViewController {

    public SetoresViewController(ViewGroupService viewGroupService) {
        super(viewGroupService);
    }

    @GetMapping("/setores")
    public ModelAndView setores() {
        return super.render("setores");
    }
}
