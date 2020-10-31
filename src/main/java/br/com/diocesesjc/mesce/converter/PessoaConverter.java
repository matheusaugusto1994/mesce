package br.com.diocesesjc.mesce.converter;

import br.com.diocesesjc.mesce.dtos.request.PessoaRequest;
import br.com.diocesesjc.mesce.entity.Pessoa;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class PessoaConverter {

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
}
