package br.com.diocesesjc.mesce.controller;

import br.com.diocesesjc.mesce.dtos.request.RegiaoRequest;
import br.com.diocesesjc.mesce.dtos.response.RegiaoResponse;
import br.com.diocesesjc.mesce.entity.Regiao;
import br.com.diocesesjc.mesce.service.RegiaoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/regiao")
public class RegiaoController {

    private final RegiaoService regiaoService;

    public RegiaoController(RegiaoService regiaoService) {
        this.regiaoService = regiaoService;
    }

    @PostMapping
    public ResponseEntity<Page<RegiaoResponse>> save(@RequestBody RegiaoRequest regiaoRequest) {
        return ResponseEntity.ok(regiaoService.save(regiaoRequest));
    }

    @GetMapping("/{page}")
    public ResponseEntity<Page<RegiaoResponse>> get(
    @PathVariable Integer page, @RequestParam(value = "q", required = false, defaultValue = "") String query){
        return ResponseEntity.ok(regiaoService.get(query, page));
    }
}
