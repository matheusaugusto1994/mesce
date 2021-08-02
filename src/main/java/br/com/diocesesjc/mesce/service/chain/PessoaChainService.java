package br.com.diocesesjc.mesce.service.chain;

import br.com.diocesesjc.mesce.entity.Paroquia;
import br.com.diocesesjc.mesce.repository.PessoaParoquiaRepository;
import br.com.diocesesjc.mesce.repository.PessoaRepository;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PessoaChainService implements ChainService {

    private final PessoaParoquiaRepository pessoaParoquiaRepository;
    private final PessoaRepository pessoaRepository;
    private final ParoquiaChainService paroquiaChainService;

    public PessoaChainService(PessoaParoquiaRepository pessoaParoquiaRepository, PessoaRepository pessoaRepository, ParoquiaChainService paroquiaChainService) {
        this.pessoaParoquiaRepository = pessoaParoquiaRepository;
        this.pessoaRepository = pessoaRepository;
        this.paroquiaChainService = paroquiaChainService;
    }

    @Override
    public Page get(String query, Pageable page) {
        Page pageParoquias = nextLevelChain(query, page);
        return Optional.of(pageParoquias)
            .map(p -> getPessoasByParoquias(p, query, page))
            .orElse(Page.empty());
    }

    @Override
    public Page nextLevelChain(String query, Pageable page) {
        return paroquiaChainService.get("", null);
    }

    private String formatQuery(String query) {
        return (query.equals("") ? "%%" : query);
    }

    private List<Long> getParoquiaIds(Page pageParoquias) {
        List<Paroquia> paroquias = pageParoquias.getContent();
        return paroquias.stream().map(Paroquia::getId).collect(Collectors.toList());
    }

    private Page getPessoasByParoquias(Page pageParoquias, String query, Pageable page) {
        List<Long> paroquiaIds = getParoquiaIds(pageParoquias);
        List<BigInteger> pessoaIds = pessoaParoquiaRepository.findAllPessoaIdsByParoquiaAndName(paroquiaIds, formatQuery(query), page).getContent();
        List<Long> ids = pessoaIds.stream().map(BigInteger::longValue).collect(Collectors.toList());
        return new PageImpl(pessoaRepository.findAllByIdAndOrderByName(ids));
    }
}
