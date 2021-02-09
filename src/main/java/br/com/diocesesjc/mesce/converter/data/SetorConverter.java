package br.com.diocesesjc.mesce.converter.data;

import br.com.diocesesjc.mesce.dtos.request.SetorRequest;
import br.com.diocesesjc.mesce.dtos.response.SetorResponse;
import br.com.diocesesjc.mesce.entity.Paroquia;
import br.com.diocesesjc.mesce.entity.Setor;
import br.com.diocesesjc.mesce.entity.Usuario;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class SetorConverter implements Converter<Setor, SetorRequest, SetorResponse> {

    private final UsuarioConverter usuarioConverter;
    private final ParoquiaConverter paroquiaConverter;

    public SetorConverter(UsuarioConverter usuarioConverter, ParoquiaConverter paroquiaConverter) {
        this.usuarioConverter = usuarioConverter;
        this.paroquiaConverter = paroquiaConverter;
    }

    @Override
    public Setor convert(SetorRequest request) {
        return Setor.builder()
            .id(request.getId())
            .name(request.getName())
            .user(Usuario.builder().id(request.getUserId()).build())
            .paroquia(Paroquia.builder().id(request.getParoquiaId()).build())
            .build();
    }

    @Override
    public SetorResponse convert(Setor setor) {
        return SetorResponse.builder()
            .id(setor.getId())
            .name(setor.getName())
            .user(usuarioConverter.toDtoResponse(setor.getUser()))
            .paroquia(paroquiaConverter.toDtoResponse(setor.getParoquia()))
            .build();
    }

    @Override
    public Page<SetorResponse> convert(Page<Setor> pages) {
        List<SetorResponse> setorResponses = convert(pages.getContent());
        return new PageImpl<>(setorResponses, pages.getPageable(), pages.getTotalElements());
    }

    @Override
    public List<SetorResponse> convert(List<Setor> setores) {
        return setores.stream()
            .map(this::convert)
            .collect(Collectors.toList());
    }
}
