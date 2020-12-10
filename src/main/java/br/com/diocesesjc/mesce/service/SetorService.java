package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.SetorConverter;
import br.com.diocesesjc.mesce.dtos.request.SetorRequest;
import br.com.diocesesjc.mesce.dtos.response.SetorResponse;
import br.com.diocesesjc.mesce.entity.Setor;
import br.com.diocesesjc.mesce.repository.SetorRepository;
import org.springframework.stereotype.Service;

@Service
public class SetorService extends
    CrudService<Setor, SetorRepository, SetorConverter, SetorRequest, SetorResponse> {

    public SetorService(SetorRepository repository, SetorConverter converter) {
        super(repository, converter);
    }
}
