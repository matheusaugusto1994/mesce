package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.data.ParoquiaConverter;
import br.com.diocesesjc.mesce.dtos.request.ParoquiaRequest;
import br.com.diocesesjc.mesce.dtos.response.ParoquiaResponse;
import br.com.diocesesjc.mesce.entity.Paroquia;
import br.com.diocesesjc.mesce.repository.ParoquiaRepository;
import br.com.diocesesjc.mesce.service.chain.ParoquiaChainService;
import br.com.diocesesjc.mesce.service.chain.PessoaChainService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ParoquiaService extends
    CrudService<Paroquia, ParoquiaRepository, ParoquiaConverter, ParoquiaRequest, ParoquiaResponse> {

    private final ParoquiaChainService paroquiaChainService;
    private final ParoquiaConverter paroquiaConverter;

    public ParoquiaService(ParoquiaRepository repository, ParoquiaConverter converter, ParoquiaChainService paroquiaChainService, ParoquiaConverter paroquiaConverter) {
        super(repository, converter);
        this.paroquiaChainService = paroquiaChainService;
        this.paroquiaConverter = paroquiaConverter;
    }

    @Override
    public Page<ParoquiaResponse> getDataByResource(String query, Pageable page) {
        Page<Paroquia> paroquias = paroquiaChainService.get(query, page);
        return paroquiaConverter.convert(paroquias);
    }
}
