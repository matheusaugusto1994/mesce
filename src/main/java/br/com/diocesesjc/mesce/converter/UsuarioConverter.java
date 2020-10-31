package br.com.diocesesjc.mesce.converter;

import br.com.diocesesjc.mesce.dtos.request.UsuarioRequest;
import br.com.diocesesjc.mesce.dtos.response.UsuarioResponse;
import br.com.diocesesjc.mesce.entity.Usuario;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter extends Converter<Usuario, UsuarioRequest, UsuarioResponse> {

    @Override
    public Usuario convert(UsuarioRequest usuarioRequest) {
        return Usuario.builder()
            .id(usuarioRequest.getId())
            .name(usuarioRequest.getName())
            .build();
    }

    @Override
    public Page<UsuarioResponse> convert(Page<Usuario> usuarios) {
        List<UsuarioResponse> usuarioResponses = convert(usuarios.getContent());
        return new PageImpl<>(usuarioResponses);
    }

    @Override
    public List<UsuarioResponse> convert(List<Usuario> usuarios) {
        return usuarios.stream()
            .map(u -> {
                UsuarioResponse response = new UsuarioResponse();
                response.setId(u.getId());
                response.setName(u.getName());
                return response;
            })
            .collect(Collectors.toList());
    }
}
