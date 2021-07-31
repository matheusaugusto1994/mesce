package br.com.diocesesjc.mesce.service.chain;

import br.com.diocesesjc.mesce.repository.PessoaParoquiaRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PessoaChainService implements ChainService {

    private final PessoaParoquiaRepository pessoaParoquiaRepository;
    private final ParoquiaChainService paroquiaChainService;

    public PessoaChainService(PessoaParoquiaRepository pessoaParoquiaRepository, ParoquiaChainService paroquiaChainService) {
        this.pessoaParoquiaRepository = pessoaParoquiaRepository;
        this.paroquiaChainService = paroquiaChainService;
    }

    @Override
    public Page get(String query, Pageable page) {
        Page paroquias = nextLevelChain(query, page);
        return Optional.of(paroquias)
            .map(p -> pessoaParoquiaRepository.findAllByParoquiaInAndPessoaNameIgnoreCaseContainingOrderByPessoaName(p.getContent(), query, page))
            .orElse(Page.empty());
    }

    @Override
    public Page nextLevelChain(String query, Pageable page) {
        return paroquiaChainService.get("", null);
    }
}
