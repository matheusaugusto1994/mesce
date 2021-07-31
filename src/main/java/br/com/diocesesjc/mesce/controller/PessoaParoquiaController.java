package br.com.diocesesjc.mesce.controller;

import br.com.diocesesjc.mesce.dtos.PessoaParoquiaRequest;
import br.com.diocesesjc.mesce.dtos.response.PessoaParoquiaResponse;
import br.com.diocesesjc.mesce.service.PessoaParoquiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoa/paroquia")
public class PessoaParoquiaController {

    private final PessoaParoquiaService pessoaParoquiaService;

    public PessoaParoquiaController(PessoaParoquiaService pessoaParoquiaService) {
        this.pessoaParoquiaService = pessoaParoquiaService;
    }

    @GetMapping("/{id}")
    public PessoaParoquiaResponse get(@PathVariable Long id) {
        return pessoaParoquiaService.getByPessoaId(id);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody PessoaParoquiaRequest pessoaParoquiaRequest) {
        pessoaParoquiaService.createOrUpdate(pessoaParoquiaRequest);
        return ResponseEntity.ok().build();
    }

}
