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
public class RegiaoConverter {

    public Regiao convert(RegiaoRequest regiaoRequest) {
        return Regiao.builder()
            .id(regiaoRequest.getId())
            .name(regiaoRequest.getName())
            .user(Usuario.builder().id(regiaoRequest.getUserId()).build())
            .build();
    }

    public Page<RegiaoResponse> convert(Page<Regiao> regioes) {
        List<RegiaoResponse> regiaoResponses = regioes.stream()
            .map(r -> RegiaoResponse.builder()
                .id(r.getId())
                .name(r.getName())
                .userId(r.getUser().getId())
                .userName(r.getUser().getName())
                .build())
            .collect(Collectors.toList());

        return new PageImpl<>(regiaoResponses);
    }
}
