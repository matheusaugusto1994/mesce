package br.com.diocesesjc.mesce.controller.view;

import br.com.diocesesjc.mesce.service.RegiaoService;
import br.com.diocesesjc.mesce.service.UsuarioService;
import br.com.diocesesjc.mesce.service.ViewGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ParoquiasViewController extends InternalViewController {

    private final RegiaoService regiaoService;
    private final UsuarioService usuarioService;

    public ParoquiasViewController(ViewGroupService viewGroupService, RegiaoService regiaoService, UsuarioService usuarioService) {
        super(viewGroupService);
        this.regiaoService = regiaoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/paroquias")
    public ModelAndView paroquias() {
        ModelAndView modelAndView = super.render("paroquias");
        modelAndView.addObject("users", usuarioService.getAll());
        modelAndView.addObject("regioes", regiaoService.getAll());
        return modelAndView;
    }
}
