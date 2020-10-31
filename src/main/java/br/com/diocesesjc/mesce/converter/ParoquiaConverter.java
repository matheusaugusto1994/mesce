package br.com.diocesesjc.mesce.converter;

import br.com.diocesesjc.mesce.dtos.request.ParoquiaRequest;
import br.com.diocesesjc.mesce.dtos.response.DtoResponse;
import br.com.diocesesjc.mesce.dtos.response.ParoquiaResponse;
import br.com.diocesesjc.mesce.entity.Paroquia;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class ParoquiaConverter extends Converter<Paroquia, ParoquiaRequest, ParoquiaResponse> {

    @Override
    public Paroquia convert(ParoquiaRequest request) {
        return Paroquia.builder()
            .id(request.getId())
            .name(request.getName())
            .userId(request.getUserId())
            .build();
    }

    @Override
    public Page<ParoquiaResponse> convert(Page<Paroquia> page) {
        List<ParoquiaResponse> paroquiaResponses = convert(page.getContent());
        return new PageImpl<>(paroquiaResponses);
    }

    @Override
    public List<ParoquiaResponse> convert(List<Paroquia> list) {
        return list.stream()
            .map(p -> {
                ParoquiaResponse response = (ParoquiaResponse) ParoquiaResponse.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .build();
                response.setUserId(p.getUserId());
                return response;
            })
            .collect(Collectors.toList());
    }
}
