package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.data.ParoquiaConverter;
import br.com.diocesesjc.mesce.dtos.request.ParoquiaRequest;
import br.com.diocesesjc.mesce.dtos.response.ParoquiaResponse;
import br.com.diocesesjc.mesce.entity.Paroquia;
import br.com.diocesesjc.mesce.repository.ParoquiaRepository;
import org.springframework.stereotype.Service;

@Service
public class ParoquiaService extends
    CrudService<Paroquia, ParoquiaRepository, ParoquiaConverter, ParoquiaRequest, ParoquiaResponse> {

    public ParoquiaService(ParoquiaRepository repository, ParoquiaConverter converter) {
        super(repository, converter);
    }
}
