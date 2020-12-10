package br.com.diocesesjc.mesce.controller;

import br.com.diocesesjc.mesce.dtos.request.SetorRequest;
import br.com.diocesesjc.mesce.service.SetorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setor")
public class SetorController extends CrudController<SetorService, SetorRequest> {

    public SetorController(SetorService setorSerice) {
        super(setorSerice);
    }
}
