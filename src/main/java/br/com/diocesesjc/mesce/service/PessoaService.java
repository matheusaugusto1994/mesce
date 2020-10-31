package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.PessoaConverter;
import br.com.diocesesjc.mesce.dtos.request.PessoaRequest;
import br.com.diocesesjc.mesce.entity.Pessoa;
import br.com.diocesesjc.mesce.repository.PessoaRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    private final PessoaConverter pessoaConverter;
    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaConverter pessoaConverter, PessoaRepository pessoaRepository) {
        this.pessoaConverter = pessoaConverter;
        this.pessoaRepository = pessoaRepository;
    }

    public Page<Pessoa> save(PessoaRequest pessoaRequest) {
        Pessoa pessoa = pessoaConverter.convert(pessoaRequest);
        pessoaRepository.save(pessoa);
        return get("", 0);
    }

    public Page<Pessoa> get(String query, Integer page) {
        return pessoaRepository.findByNameIgnoreCaseContainingOrderByName(query, PageRequest.of(page, 10));
    }

    public void delete(Long id) {
        pessoaRepository.delete(Pessoa.builder().id(id).build());
    }

    public List<Pessoa> getAll() {
        return (List<Pessoa>) pessoaRepository.findAll();
    }
}
