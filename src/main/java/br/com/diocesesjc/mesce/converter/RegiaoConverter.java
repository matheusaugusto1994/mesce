package br.com.diocesesjc.mesce.converter;

import br.com.diocesesjc.mesce.dtos.request.RegiaoRequest;
import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import br.com.diocesesjc.mesce.dtos.response.RegiaoResponse;
import br.com.diocesesjc.mesce.entity.Regiao;
import br.com.diocesesjc.mesce.entity.Usuario;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class RegiaoConverter extends Converter<Regiao, RegiaoRequest, RegiaoResponse> {

    private final UsuarioConverter usuarioConverter;

    public RegiaoConverter(UsuarioConverter usuarioConverter) {
        this.usuarioConverter = usuarioConverter;
    }

    @Override
    public Regiao convert(RegiaoRequest regiaoRequest) {
        return Regiao.builder()
            .id(regiaoRequest.getId())
            .name(regiaoRequest.getName())
            .user(Usuario.builder().id(regiaoRequest.getUserId()).build())
            .build();
    }

    @Override
    public RegiaoResponse convert(Regiao regiao) {
        return RegiaoResponse.builder()
            .id(regiao.getId())
            .name(regiao.getName())
            .user(usuarioConverter.toDtoResponse(regiao.getUser()))
            .build();
    }

    @Override
    public Page<RegiaoResponse> convert(Page<Regiao> regioes) {
        List<RegiaoResponse> regiaoResponses = convert(regioes.getContent());
        return new PageImpl<>(regiaoResponses, regioes.getPageable(), regioes.getTotalElements());
    }

    @Override
    public List<RegiaoResponse> convert(List<Regiao> list) {
        return list.stream()
            .map(this::convert)
            .collect(Collectors.toList());
    }

    public DtoResponse toDtoResponse(Regiao regiao) {
        return DtoResponse.builder()
            .id(regiao.getId())
            .name(regiao.getName())
            .build();
    }
}
