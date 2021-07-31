package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.data.ParoquiaConverter;
import br.com.diocesesjc.mesce.dtos.request.ParoquiaRequest;
import br.com.diocesesjc.mesce.dtos.response.ParoquiaResponse;
import br.com.diocesesjc.mesce.entity.Paroquia;
import br.com.diocesesjc.mesce.repository.ParoquiaRepository;
import br.com.diocesesjc.mesce.service.chain.ParoquiaChainService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ParoquiaService extends
    CrudService<Paroquia, ParoquiaRepository, ParoquiaConverter, ParoquiaRequest, ParoquiaResponse> {

    private final ParoquiaRepository paroquiaRepository;
    private final ParoquiaChainService paroquiaChainService;
    private final ParoquiaConverter paroquiaConverter;
    private final PessoaParoquiaService pessoaParoquiaService;

    public ParoquiaService(ParoquiaRepository repository, ParoquiaConverter converter, ParoquiaChainService paroquiaChainService, ParoquiaConverter paroquiaConverter, PessoaParoquiaService pessoaParoquiaService) {
        super(repository, converter);
        this.paroquiaRepository = repository;
        this.paroquiaChainService = paroquiaChainService;
        this.paroquiaConverter = paroquiaConverter;
        this.pessoaParoquiaService = pessoaParoquiaService;
    }

    @Override
    public Page<ParoquiaResponse> getDataByResource(String query, Pageable page) {
        Page<Paroquia> paroquias = paroquiaChainService.get(query, page);
        return paroquiaConverter.convert(paroquias);
    }

    public List<ParoquiaResponse> getAllByRegiao(Long regiaoId) {
        List<Paroquia> paroquias = paroquiaRepository.findAllByRegiaoId(regiaoId);
        return paroquiaConverter.convert(paroquias);
    }

    @Override
    public Paroquia save(ParoquiaRequest request) {
        Paroquia paroquia = super.save(request);
        pessoaParoquiaService.updateRelation(paroquia);
        return paroquia;
    }
}
