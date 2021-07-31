package br.com.diocesesjc.mesce.controller;

import br.com.diocesesjc.mesce.dtos.request.RegiaoRequest;
import br.com.diocesesjc.mesce.service.RegiaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/regiao")
public class RegiaoController extends CrudController<RegiaoService, RegiaoRequest> {

    private final RegiaoService regiaoService;

    public RegiaoController(RegiaoService regiaoService) {
        super(regiaoService);
        this.regiaoService = regiaoService;
    }

    @GetMapping("/all")
    public ResponseEntity getAllByPage() {
        return ResponseEntity.ok(regiaoService.getAll());
    }
}
