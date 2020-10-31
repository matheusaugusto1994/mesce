package br.com.diocesesjc.mesce.controller.view;

import br.com.diocesesjc.mesce.service.ViewGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ParoquiasViewController extends InternalViewController {

    public ParoquiasViewController(ViewGroupService viewGroupService) {
        super(viewGroupService);
    }

    @GetMapping("/paroquias")
    public ModelAndView paroquias() {
        return super.render("paroquias");
    }
}
