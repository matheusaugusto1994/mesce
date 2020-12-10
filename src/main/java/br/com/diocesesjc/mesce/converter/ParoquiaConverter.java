package br.com.diocesesjc.mesce.converter;

import br.com.diocesesjc.mesce.dtos.request.ParoquiaRequest;
import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import br.com.diocesesjc.mesce.dtos.response.ParoquiaResponse;
import br.com.diocesesjc.mesce.entity.Paroquia;
import br.com.diocesesjc.mesce.entity.Regiao;
import br.com.diocesesjc.mesce.entity.Usuario;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class ParoquiaConverter extends Converter<Paroquia, ParoquiaRequest, ParoquiaResponse> {

    private final RegiaoConverter regiaoConverter;
    private final UsuarioConverter usuarioConverter;

    public ParoquiaConverter(RegiaoConverter regiaoConverter, UsuarioConverter usuarioConverter) {
        this.regiaoConverter = regiaoConverter;
        this.usuarioConverter = usuarioConverter;
    }

    @Override
    public Paroquia convert(ParoquiaRequest request) {
        return Paroquia.builder()
            .id(request.getId())
            .name(request.getName())
            .address(request.getAddress())
            .phone(request.getPhone())
            .regiao(Regiao.builder().id(request.getRegiaoId()).build())
            .user(Usuario.builder().id(request.getUserId()).build())
            .build();
    }

    @Override
    public ParoquiaResponse convert(Paroquia paroquia) {
        return ParoquiaResponse.builder()
            .id(paroquia.getId())
            .name(paroquia.getName())
            .phone(paroquia.getPhone())
            .address(paroquia.getAddress())
            .regiao(regiaoConverter.toDtoResponse(paroquia.getRegiao()))
            .user(usuarioConverter.toDtoResponse(paroquia.getUser()))
            .build();
    }

    @Override
    public Page<ParoquiaResponse> convert(Page<Paroquia> page) {
        List<ParoquiaResponse> paroquiaResponses = convert(page.getContent());
        return new PageImpl<>(paroquiaResponses, page.getPageable(), page.getTotalElements());
    }

    @Override
    public List<ParoquiaResponse> convert(List<Paroquia> list) {
        return list.stream()
            .map(this::convert)
            .collect(Collectors.toList());
    }

    public DtoResponse toDtoResponse(Paroquia paroquia) {
        return DtoResponse.builder()
            .id(paroquia.getId())
            .name(paroquia.getName())
            .build();
    }
}
