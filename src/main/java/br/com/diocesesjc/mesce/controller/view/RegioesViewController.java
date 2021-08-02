package br.com.diocesesjc.mesce.controller.view;

import static br.com.diocesesjc.mesce.enums.RoleType.ROLE_COORDENADOR_REGIAO;
import static br.com.diocesesjc.mesce.enums.RoleType.ROLE_SUPERVISOR;

import br.com.diocesesjc.mesce.enums.RoleType;
import br.com.diocesesjc.mesce.service.UsuarioService;
import br.com.diocesesjc.mesce.service.ViewGroupService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegioesViewController extends InternalViewController {

    private final UsuarioService usuarioService;

    public RegioesViewController(ViewGroupService viewGroupService, UsuarioService usuarioService) {
        super(viewGroupService);
        this.usuarioService = usuarioService;
    }

    @GetMapping("/regioes")
    public ModelAndView regioes() {
        ModelAndView modelAndView = super.render("regioes");
        modelAndView.addObject("users", usuarioService.getAllByRole(List.of(ROLE_SUPERVISOR, ROLE_COORDENADOR_REGIAO)));
        return modelAndView;
    }
}
