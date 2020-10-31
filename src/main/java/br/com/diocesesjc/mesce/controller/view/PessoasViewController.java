package br.com.diocesesjc.mesce.controller.view;

import br.com.diocesesjc.mesce.service.ViewGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PessoasViewController extends InternalViewController {

    public PessoasViewController(ViewGroupService viewGroupService) {
        super(viewGroupService);
    }

    @GetMapping("/pessoas")
    public ModelAndView pessoas() {
        return super.render("pessoas");
    }
}
