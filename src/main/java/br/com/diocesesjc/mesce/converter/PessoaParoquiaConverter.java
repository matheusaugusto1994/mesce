package br.com.diocesesjc.mesce.converter;

import br.com.diocesesjc.mesce.converter.data.ParoquiaConverter;
import br.com.diocesesjc.mesce.converter.data.PessoaConverter;
import br.com.diocesesjc.mesce.dtos.PessoaParoquiaRequest;
import br.com.diocesesjc.mesce.dtos.response.PessoaParoquiaResponse;
import br.com.diocesesjc.mesce.entity.Paroquia;
import br.com.diocesesjc.mesce.entity.Pessoa;
import br.com.diocesesjc.mesce.entity.PessoaParoquia;
import br.com.diocesesjc.mesce.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class PessoaParoquiaConverter {

    private final PessoaConverter pessoaConverter;
    private final ParoquiaConverter paroquiaConverter;

    public PessoaParoquiaConverter(PessoaConverter pessoaConverter, ParoquiaConverter paroquiaConverter) {
        this.pessoaConverter = pessoaConverter;
        this.paroquiaConverter = paroquiaConverter;
    }

    public PessoaParoquiaResponse convert(PessoaParoquia pessoaParoquia) {
        return PessoaParoquiaResponse.builder()
            .pessoa(pessoaConverter.convert(pessoaParoquia.getPessoa()))
            .paroquia(paroquiaConverter.convert(pessoaParoquia.getParoquia()))
            .build();
    }

    public PessoaParoquiaResponse convert(Pessoa pessoa) {
        return PessoaParoquiaResponse.builder()
            .pessoa(pessoaConverter.convert(pessoa))
            .build();
    }

    public PessoaParoquia convert(PessoaParoquiaRequest pessoaParoquiaRequest) {
        return PessoaParoquia.builder()
            .paroquia(Paroquia.builder().id(pessoaParoquiaRequest.getParoquia()).build())
            .pessoa(Pessoa.builder().id(pessoaParoquiaRequest.getPessoa()).build())
            .build();
    }

    public PessoaParoquia convert(Paroquia paroquia) {
        return PessoaParoquia.builder()
            .paroquia(paroquia)
            .pessoa(paroquia.getUser().getPessoa())
            .build();
    }

    public PessoaParoquia convert(Paroquia paroquia, Usuario usuario) {
        return PessoaParoquia.builder()
            .paroquia(paroquia)
            .pessoa(usuario.getPessoa())
            .build();
    }
}
