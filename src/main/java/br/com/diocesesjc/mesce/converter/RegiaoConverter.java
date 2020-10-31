package br.com.diocesesjc.mesce.converter;

import br.com.diocesesjc.mesce.dtos.request.RegiaoRequest;
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

    @Override
    public Regiao convert(RegiaoRequest regiaoRequest) {
        return Regiao.builder()
            .id(regiaoRequest.getId())
            .name(regiaoRequest.getName())
            .user(Usuario.builder().id(regiaoRequest.getUserId()).build())
            .build();
    }

    @Override
    public Page<RegiaoResponse> convert(Page<Regiao> regioes) {
        List<RegiaoResponse> regiaoResponses = convert(regioes.getContent());
        return new PageImpl<>(regiaoResponses);
    }

    @Override
    public List<RegiaoResponse> convert(List<Regiao> list) {
        return list.stream()
            .map(r -> {
                RegiaoResponse response = new RegiaoResponse();
                response.setId(r.getId());
                response.setName(r.getName());
                response.setUserId(r.getUser().getId());
                response.setUserName(r.getUser().getName());
                return response;
            })
            .collect(Collectors.toList());
    }
}
