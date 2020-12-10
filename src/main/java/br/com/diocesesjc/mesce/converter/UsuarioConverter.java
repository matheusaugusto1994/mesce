package br.com.diocesesjc.mesce.converter;

import br.com.diocesesjc.mesce.dtos.request.UsuarioRequest;
import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import br.com.diocesesjc.mesce.dtos.response.UsuarioResponse;
import br.com.diocesesjc.mesce.entity.Pessoa;
import br.com.diocesesjc.mesce.entity.Role;
import br.com.diocesesjc.mesce.entity.Usuario;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter extends Converter<Usuario, UsuarioRequest, UsuarioResponse> {

    private final PessoaConverter pessoaConverter;
    private final RoleConverter roleConverter;

    public UsuarioConverter(PessoaConverter pessoaConverter, RoleConverter roleConverter) {
        this.pessoaConverter = pessoaConverter;
        this.roleConverter = roleConverter;
    }

    @Override
    public Usuario convert(UsuarioRequest usuarioRequest) {
        return Usuario.builder()
            .id(usuarioRequest.getId())
            .name(usuarioRequest.getName())
            .password(usuarioRequest.getPassword())
            .blocked(usuarioRequest.isBlocked())
            .pessoa(Pessoa.builder().id(usuarioRequest.getPessoaId()).build())
            .role(Role.builder().id(usuarioRequest.getRoleId()).build())
            .build();
    }

    @Override
    public UsuarioResponse convert(Usuario usuario) {
        return UsuarioResponse.builder()
            .id(usuario.getId())
            .name(usuario.getName())
            .password(usuario.getPassword())
            .role(roleConverter.convert(usuario.getRole()))
            .pessoa(pessoaConverter.convert(usuario.getPessoa()))
            .build();
    }

    @Override
    public Page<UsuarioResponse> convert(Page<Usuario> usuarios) {
        List<UsuarioResponse> usuarioResponses = convert(usuarios.getContent());
        return new PageImpl<>(usuarioResponses, usuarios.getPageable(), usuarios.getTotalElements());
    }

    @Override
    public List<UsuarioResponse> convert(List<Usuario> usuarios) {
        return usuarios.stream()
            .map(this::convert)
            .collect(Collectors.toList());
    }

    public DtoResponse toDtoResponse(Usuario usuario) {
        return DtoResponse.builder()
            .id(usuario.getId())
            .name(usuario.getPessoa().getName())
            .build();
    }
}
