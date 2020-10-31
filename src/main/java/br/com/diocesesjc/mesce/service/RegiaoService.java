package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.RegiaoConverter;
import br.com.diocesesjc.mesce.dtos.request.RegiaoRequest;
import br.com.diocesesjc.mesce.dtos.response.RegiaoResponse;
import br.com.diocesesjc.mesce.entity.Regiao;
import br.com.diocesesjc.mesce.repository.RegiaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RegiaoService {

    private final RegiaoConverter regiaoConverter;
    private final RegiaoRepository regiaoRepository;

    public RegiaoService(RegiaoConverter regiaoConverter, RegiaoRepository regiaoRepository) {
        this.regiaoConverter = regiaoConverter;
        this.regiaoRepository = regiaoRepository;
    }

    public Page<RegiaoResponse> save(RegiaoRequest regiaoRequest) {
        Regiao regiao = regiaoConverter.convert(regiaoRequest);
        regiaoRepository.save(regiao);
        return get("", 0);
    }

    public Page<RegiaoResponse> get(String query, Integer page) {
        Page<Regiao> regioes = regiaoRepository.findByNameIgnoreCaseContainingOrderByName(query, PageRequest.of(page, 10));
        return regiaoConverter.convert(regioes);
    }
}
