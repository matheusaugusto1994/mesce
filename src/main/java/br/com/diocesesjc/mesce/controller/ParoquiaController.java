package br.com.diocesesjc.mesce.controller;

import br.com.diocesesjc.mesce.dtos.request.ParoquiaRequest;
import br.com.diocesesjc.mesce.service.ParoquiaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paroquia")
public class ParoquiaController extends CrudController<ParoquiaService, ParoquiaRequest> {

    public ParoquiaController(ParoquiaService paroquiaService) {
        super(paroquiaService);
    }
}
