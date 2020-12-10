package br.com.diocesesjc.mesce.controller.view;

import br.com.diocesesjc.mesce.service.ParoquiaService;
import br.com.diocesesjc.mesce.service.UsuarioService;
import br.com.diocesesjc.mesce.service.ViewGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SetoresViewController extends InternalViewController {

    private final UsuarioService usuarioService;
    private final ParoquiaService paroquiaService;

    public SetoresViewController(ViewGroupService viewGroupService, UsuarioService usuarioService, ParoquiaService paroquiaService) {
        super(viewGroupService);
        this.usuarioService = usuarioService;
        this.paroquiaService = paroquiaService;
    }

    @GetMapping("/setores")
    public ModelAndView setores() {
        ModelAndView modelAndView = super.render("setores");
        modelAndView.addObject("users", usuarioService.getAll());
        modelAndView.addObject("paroquias", paroquiaService.getAll());
        return modelAndView;
    }
}
