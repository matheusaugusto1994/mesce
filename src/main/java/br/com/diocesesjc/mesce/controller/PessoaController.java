package br.com.diocesesjc.mesce.controller;

import br.com.diocesesjc.mesce.dtos.request.PessoaRequest;
import br.com.diocesesjc.mesce.dtos.response.PessoaResponse;
import br.com.diocesesjc.mesce.service.PessoaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoa")
public class PessoaController extends CrudController<PessoaService, PessoaRequest> {

    PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        super(pessoaService);
        this.pessoaService = pessoaService;
    }

    @GetMapping("/{id}/photo")
    public String getPhoto(@PathVariable Long id) {
        return pessoaService.loadPhoto(id);
    }
}
