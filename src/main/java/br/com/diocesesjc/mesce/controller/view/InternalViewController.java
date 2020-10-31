package br.com.diocesesjc.mesce.controller.view;

import br.com.diocesesjc.mesce.service.ViewGroupService;
import org.springframework.web.servlet.ModelAndView;

public abstract class InternalViewController {

    private final ViewGroupService viewGroupService;

    public InternalViewController(ViewGroupService viewGroupService) {
        this.viewGroupService = viewGroupService;
    }

    public ModelAndView render(String templateName) {
        ModelAndView modelAndView = new ModelAndView(templateName);
        modelAndView.addObject("viewGroup", viewGroupService.getAll());
        return modelAndView;
    }
}
