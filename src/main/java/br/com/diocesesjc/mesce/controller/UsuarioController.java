package br.com.diocesesjc.mesce.controller;

import br.com.diocesesjc.mesce.dtos.request.UsuarioRequest;
import br.com.diocesesjc.mesce.service.UsuarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController extends CrudController<UsuarioService, UsuarioRequest> {

    public UsuarioController(UsuarioService usuarioService) {
        super(usuarioService);
    }
}
