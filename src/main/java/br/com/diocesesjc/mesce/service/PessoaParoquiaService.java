package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.PessoaParoquiaConverter;
import br.com.diocesesjc.mesce.dtos.PessoaParoquiaRequest;
import br.com.diocesesjc.mesce.dtos.response.PessoaParoquiaResponse;
import br.com.diocesesjc.mesce.entity.Paroquia;
import br.com.diocesesjc.mesce.entity.Pessoa;
import br.com.diocesesjc.mesce.entity.PessoaParoquia;
import br.com.diocesesjc.mesce.entity.Regiao;
import br.com.diocesesjc.mesce.repository.PessoaParoquiaRepository;
import br.com.diocesesjc.mesce.repository.PessoaRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessoaParoquiaService {

    private final PessoaParoquiaRepository pessoaParoquiaRepository;
    private final PessoaParoquiaConverter pessoaParoquiaConverter;
    private final PessoaRepository pessoaRepository;

    public PessoaParoquiaService(PessoaParoquiaRepository pessoaParoquiaRepository, PessoaParoquiaConverter pessoaParoquiaConverter, PessoaRepository pessoaRepository) {
        this.pessoaParoquiaRepository = pessoaParoquiaRepository;
        this.pessoaParoquiaConverter = pessoaParoquiaConverter;
        this.pessoaRepository = pessoaRepository;
    }

    public PessoaParoquiaResponse getByPessoaId(Long pessoaId) {
        PessoaParoquia pessoaParoquia = pessoaParoquiaRepository.findByPessoaId(pessoaId);
        return Optional.ofNullable(pessoaParoquia)
            .map(pessoaParoquiaConverter::convert)
            .orElseGet(() -> {
                Pessoa pessoa = pessoaRepository.getOne(pessoaId);
                return pessoaParoquiaConverter.convert(pessoa);
            });
    }

    public void createOrUpdate(PessoaParoquiaRequest pessoaParoquiaRequest) {
        PessoaParoquia pessoaParoquia = pessoaParoquiaConverter.convert(pessoaParoquiaRequest);
        createOrUpdate(pessoaParoquia, pessoaParoquiaRequest.getPessoa());
    }

    public void updateRelation(Paroquia paroquia) {
        PessoaParoquia pessoaParoquia = pessoaParoquiaConverter.convert(paroquia);
        createOrUpdate(pessoaParoquia, pessoaParoquia.getPessoa().getId());
    }

    public void updateRelation(Regiao regiao) {

    }

    @Transactional
    void createOrUpdate(PessoaParoquia pessoaParoquia, Long pessoaId) {
        PessoaParoquia loadedPessoaParoquia = pessoaParoquiaRepository.findByPessoaId(pessoaId);

        Optional.ofNullable(loadedPessoaParoquia)
            .ifPresentOrElse(pr -> pr.setParoquia(pessoaParoquia.getParoquia()),
                () -> pessoaParoquiaRepository.save(pessoaParoquia));
    }
}
