package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.data.RoleConverter;
import br.com.diocesesjc.mesce.dtos.request.DtoRequest;
import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import br.com.diocesesjc.mesce.entity.Role;
import br.com.diocesesjc.mesce.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends
    CrudService<Role, RoleRepository, RoleConverter, DtoRequest, DtoResponse> {

    public RoleService(RoleRepository repository, RoleConverter converter) {
        super(repository, converter);
    }
}
