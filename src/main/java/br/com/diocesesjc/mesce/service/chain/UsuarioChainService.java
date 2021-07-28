package br.com.diocesesjc.mesce.service.chain;

import br.com.diocesesjc.mesce.repository.UsuarioRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioChainService implements ChainService {

    private final UsuarioRepository usuarioRepository;
    private final PessoaChainService pessoaChainService;

    public UsuarioChainService(UsuarioRepository usuarioRepository, PessoaChainService pessoaChainService) {
        this.usuarioRepository = usuarioRepository;
        this.pessoaChainService = pessoaChainService;
    }

    @Override
    public Page get(String query, Pageable page) {
        Page pessoas = nextLevelChain(query, page);
        return Optional.of(pessoas)
            .map(p -> usuarioRepository.findAllByPessoaInAndNameIgnoreCaseContainingOrderByName(p.getContent(), query, page))
            .orElse(Page.empty());
    }

    @Override
    public Page nextLevelChain(String query, Pageable page) {
        return pessoaChainService.get("", null);
    }
}
