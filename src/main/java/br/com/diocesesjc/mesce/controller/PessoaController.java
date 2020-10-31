package br.com.diocesesjc.mesce.controller;

import br.com.diocesesjc.mesce.dtos.request.PessoaRequest;
import br.com.diocesesjc.mesce.entity.Pessoa;
import br.com.diocesesjc.mesce.service.PessoaService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<Page<Pessoa>> save(@RequestBody PessoaRequest pessoaRequest) {
        return ResponseEntity.ok(pessoaService.save(pessoaRequest));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        pessoaService.delete(id);
    }

    @PutMapping
    public ResponseEntity<Page<Pessoa>> update(@RequestBody PessoaRequest pessoaRequest) {
        return ResponseEntity.ok(pessoaService.save(pessoaRequest));
    }

    @GetMapping("/{page}")
    public ResponseEntity<Page<Pessoa>> getAllByPage(
        @PathVariable Integer page, @RequestParam(value = "q", required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(pessoaService.get(query, page));
    }
}
