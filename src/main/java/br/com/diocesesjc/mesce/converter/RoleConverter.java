package br.com.diocesesjc.mesce.converter;

import br.com.diocesesjc.mesce.dtos.request.DtoRequest;
import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import br.com.diocesesjc.mesce.entity.Role;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter extends Converter<Role, DtoRequest, DtoResponse> {

    @Override
    public Role convert(DtoRequest request) {
        return null;
    }

    @Override
    public DtoResponse convert(Role role) {
        return DtoResponse.builder()
            .id(role.getId())
            .name(role.getDescription())
            .build();
    }

    @Override
    public Page<DtoResponse> convert(Page<Role> page) {
        return null;
    }

    @Override
    public List<DtoResponse> convert(List<Role> roles) {
        return roles.stream()
            .map(this::convert)
            .collect(Collectors.toList());
    }
}
