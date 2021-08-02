package br.com.diocesesjc.mesce.controller.view;

import static br.com.diocesesjc.mesce.enums.RoleType.ROLE_MINISTRO;

import br.com.diocesesjc.mesce.enums.RoleType;
import br.com.diocesesjc.mesce.service.PessoaService;
import br.com.diocesesjc.mesce.service.RegiaoService;
import br.com.diocesesjc.mesce.service.ViewGroupService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AssociarMinistrosViewController extends InternalViewController {

    private final PessoaService pessoaService;
    private final RegiaoService regiaoService;

    public AssociarMinistrosViewController(ViewGroupService viewGroupService, PessoaService pessoaService, RegiaoService regiaoService) {
        super(viewGroupService);
        this.pessoaService = pessoaService;
        this.regiaoService = regiaoService;
    }

    @RequestMapping("/associar")
    public ModelAndView associar() {
        ModelAndView modelAndView = super.render("associarMinistros");
        modelAndView.addObject("pessoas", pessoaService.findAllByRole(ROLE_MINISTRO));
        modelAndView.addObject("regioes", regiaoService.getAll());
        return modelAndView;
    }
}
