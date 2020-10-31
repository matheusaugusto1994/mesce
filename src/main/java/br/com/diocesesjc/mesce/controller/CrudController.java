package br.com.diocesesjc.mesce.controller;

import br.com.diocesesjc.mesce.dtos.request.DtoRequest;
import br.com.diocesesjc.mesce.dtos.request.UsuarioRequest;
import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import br.com.diocesesjc.mesce.dtos.response.UsuarioResponse;
import br.com.diocesesjc.mesce.service.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class CrudController<T extends CrudService, REQ extends DtoRequest> {

    private final T service;

    CrudController(T service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody REQ request) {
        return ResponseEntity.ok(service.save(request));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody REQ request) {
        return ResponseEntity.ok(service.save(request));
    }

    @GetMapping("/{page}")
    public ResponseEntity getAllByPage(
        @PathVariable Integer page, @RequestParam(value = "q", required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(service.get(query, page));
    }

}
