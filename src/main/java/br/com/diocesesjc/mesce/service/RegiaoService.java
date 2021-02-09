package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.data.RegiaoConverter;
import br.com.diocesesjc.mesce.dtos.request.RegiaoRequest;
import br.com.diocesesjc.mesce.dtos.response.RegiaoResponse;
import br.com.diocesesjc.mesce.entity.Regiao;
import br.com.diocesesjc.mesce.repository.RegiaoRepository;
import org.springframework.stereotype.Service;

@Service
public class RegiaoService extends CrudService<Regiao, RegiaoRepository, RegiaoConverter, RegiaoRequest, RegiaoResponse> {

    public RegiaoService(RegiaoConverter regiaoConverter, RegiaoRepository regiaoRepository) {
        super(regiaoRepository, regiaoConverter);
    }
}
