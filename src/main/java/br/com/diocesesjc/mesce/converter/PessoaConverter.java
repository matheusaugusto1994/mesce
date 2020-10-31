package br.com.diocesesjc.mesce.converter;

import br.com.diocesesjc.mesce.dtos.request.PessoaRequest;
import br.com.diocesesjc.mesce.dtos.response.PessoaResponse;
import br.com.diocesesjc.mesce.entity.Pessoa;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class PessoaConverter extends Converter<Pessoa, PessoaRequest, PessoaResponse> {

    @Override
    public Pessoa convert(PessoaRequest pessoaRequest) {
        return Pessoa.builder()
            .id(pessoaRequest.getId())
            .name(pessoaRequest.getName())
            .address(pessoaRequest.getAddress())
            .birthDate(LocalDate.parse(pessoaRequest.getBirthDate()))
            .phone(pessoaRequest.getPhone())
            .email(pessoaRequest.getEmail())
            .build();
    }

    @Override
    public Page<PessoaResponse> convert(Page<Pessoa> page) {
        List<PessoaResponse> pessoaResponses = convert(page.getContent());
        return new PageImpl<>(pessoaResponses);
    }

    @Override
    public List<PessoaResponse> convert(List<Pessoa> list) {
        return list.stream()
            .map(p -> {
                PessoaResponse response = new PessoaResponse();
                response.setId(p.getId());
                response.setName(p.getName());
                response.setAddress(p.getAddress());
                response.setBirthDate(p.getBirthDate());
                response.setEmail(p.getEmail());
                response.setPhone(p.getPhone());
                return response;
            })
            .collect(Collectors.toList());
    }
}
