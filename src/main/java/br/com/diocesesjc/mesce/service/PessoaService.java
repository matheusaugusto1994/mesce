package br.com.diocesesjc.mesce.service;

import static br.com.diocesesjc.mesce.utils.FileManager.convertToBase64;

import br.com.diocesesjc.mesce.converter.data.PessoaConverter;
import br.com.diocesesjc.mesce.dtos.request.PessoaRequest;
import br.com.diocesesjc.mesce.dtos.response.PessoaResponse;
import br.com.diocesesjc.mesce.entity.Pessoa;
import br.com.diocesesjc.mesce.repository.PessoaRepository;
import org.springframework.stereotype.Service;

@Service
public class PessoaService extends
    CrudService<Pessoa, PessoaRepository, PessoaConverter, PessoaRequest, PessoaResponse> {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaConverter pessoaConverter, PessoaRepository pessoaRepository) {
        super(pessoaRepository, pessoaConverter);
        this.pessoaRepository = pessoaRepository;
    }

    public String loadPhoto(Long pessoaId) {
        Pessoa pessoa = pessoaRepository.getOne(pessoaId);
        return convertToBase64(pessoa.getPhotoPath());
    }
}
