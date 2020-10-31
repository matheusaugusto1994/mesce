package br.com.diocesesjc.mesce.controller.view;

import br.com.diocesesjc.mesce.service.PessoaService;
import br.com.diocesesjc.mesce.service.ViewGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsuariosViewController extends InternalViewController {

    private final PessoaService pessoaService;

    public UsuariosViewController(ViewGroupService viewGroupService, PessoaService pessoaService) {
        super(viewGroupService);
        this.pessoaService = pessoaService;
    }

    @GetMapping("/usuarios")
    public ModelAndView pessoas() {
        ModelAndView modelAndView = super.render("usuarios");
        modelAndView.addObject("pessoas", pessoaService.getAll());
        return modelAndView;
    }
}
