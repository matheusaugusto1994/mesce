package br.com.diocesesjc.mesce.controller;

import br.com.diocesesjc.mesce.dtos.request.DtoRequest;
import br.com.diocesesjc.mesce.service.CrudService;
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
    public ResponseEntity<String> save(@RequestBody REQ request) {
        service.save(request);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/all/{page}")
    public ResponseEntity getAllByPage(
        @PathVariable Integer page, @RequestParam(value = "q", required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(service.get(query, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
