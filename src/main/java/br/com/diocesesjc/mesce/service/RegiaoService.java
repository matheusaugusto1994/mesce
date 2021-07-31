package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.data.RegiaoConverter;
import br.com.diocesesjc.mesce.dtos.request.RegiaoRequest;
import br.com.diocesesjc.mesce.dtos.response.RegiaoResponse;
import br.com.diocesesjc.mesce.entity.Regiao;
import br.com.diocesesjc.mesce.repository.RegiaoRepository;
import br.com.diocesesjc.mesce.service.chain.RegiaoChainService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RegiaoService extends CrudService<Regiao, RegiaoRepository, RegiaoConverter, RegiaoRequest, RegiaoResponse> {

    private final RegiaoChainService regiaoChainService;
    private final RegiaoConverter regiaoConverter;
    private final PessoaParoquiaService pessoaParoquiaService;

    public RegiaoService(RegiaoConverter regiaoConverter, RegiaoRepository regiaoRepository, RegiaoChainService regiaoChainService, RegiaoConverter regiaoConverter1, PessoaParoquiaService pessoaParoquiaService) {
        super(regiaoRepository, regiaoConverter);
        this.regiaoChainService = regiaoChainService;
        this.regiaoConverter = regiaoConverter1;
        this.pessoaParoquiaService = pessoaParoquiaService;
    }

    @Override
    public Page<RegiaoResponse> getDataByResource(String query, Pageable page) {
        Page<Regiao> regioes = regiaoChainService.get(query, page);
        return regiaoConverter.convert(regioes);
    }

    @Override
    public Regiao save(RegiaoRequest request) {
        Regiao regiao = super.save(request);
        pessoaParoquiaService.updateRelation(regiao);
        return regiao;
    }
}
