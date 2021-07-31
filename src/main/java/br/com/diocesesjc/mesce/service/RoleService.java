package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.data.RoleConverter;
import br.com.diocesesjc.mesce.dtos.request.DtoRequest;
import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import br.com.diocesesjc.mesce.entity.Role;
import br.com.diocesesjc.mesce.repository.RoleRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleConverter roleConverter;
    private final RoleRepository roleRepository;

    public RoleService(RoleConverter roleConverter, RoleRepository roleRepository) {
        this.roleConverter = roleConverter;
        this.roleRepository = roleRepository;
    }

    public List<DtoResponse> getAll() {
        List<Role> roles = roleRepository.findAll();
        return roleConverter.convert(roles);
    }
}
