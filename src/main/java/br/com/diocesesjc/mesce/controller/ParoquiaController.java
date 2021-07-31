package br.com.diocesesjc.mesce.controller;

import br.com.diocesesjc.mesce.dtos.request.ParoquiaRequest;
import br.com.diocesesjc.mesce.dtos.response.ParoquiaResponse;
import br.com.diocesesjc.mesce.service.ParoquiaService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paroquia")
public class ParoquiaController extends CrudController<ParoquiaService, ParoquiaRequest> {

    private final ParoquiaService paroquiaService;

    public ParoquiaController(ParoquiaService paroquiaService) { super(paroquiaService);
        this.paroquiaService = paroquiaService;
    }

    @GetMapping("/regiao/{id}")
    public List<ParoquiaResponse> getAllByRegiao(@PathVariable Long id) {
        return paroquiaService.getAllByRegiao(id);
    }
}
