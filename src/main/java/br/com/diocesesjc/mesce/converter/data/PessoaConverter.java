package br.com.diocesesjc.mesce.converter.data;

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
public class PessoaConverter implements Converter<Pessoa, PessoaRequest, PessoaResponse> {

    @Override
    public Pessoa convert(PessoaRequest pessoaRequest) {
        return Pessoa.builder()
            .id(pessoaRequest.getId())
            .name(pessoaRequest.getName())
            .address(pessoaRequest.getAddress())
            .phone(pessoaRequest.getPhone())
            .email(pessoaRequest.getEmail())
            .build();
    }

    @Override
    public PessoaResponse convert(Pessoa pessoa) {
        return PessoaResponse.builder()
            .id(pessoa.getId())
            .name(pessoa.getName())
            .address(pessoa.getAddress())
            .email(pessoa.getEmail())
            .phone(pessoa.getPhone())
            .build();
    }

    @Override
    public Page<PessoaResponse> convert(Page<Pessoa> page) {
        List<PessoaResponse> pessoaResponses = convert(page.getContent());
        return new PageImpl<>(pessoaResponses, page.getPageable(), page.getTotalElements());
    }

    @Override
    public List<PessoaResponse> convert(List<Pessoa> list) {
        return list.stream()
            .map(this::convert)
            .collect(Collectors.toList());
    }
}
