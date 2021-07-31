package br.com.diocesesjc.mesce.service.chain;

import br.com.diocesesjc.mesce.entity.Usuario;
import br.com.diocesesjc.mesce.enums.RoleType;
import br.com.diocesesjc.mesce.repository.ParoquiaRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ParoquiaChainService implements ChainService {

    private final ParoquiaRepository paroquiaRepository;
    private final RegiaoChainService regiaoChainService;

    public ParoquiaChainService(ParoquiaRepository paroquiaRepository, RegiaoChainService regiaoChainService) {
        this.paroquiaRepository = paroquiaRepository;
        this.regiaoChainService = regiaoChainService;
    }

    @Override
    public Page get(String query, Pageable page) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (usuario.getRole().getName() == RoleType.ROLE_COORDENADOR_PASTORAL) {
            return paroquiaRepository.findAllByUserIdAndNameIgnoreCaseContainingOrderByName(usuario.getId(), query, page);
        }

        Page regioes = nextLevelChain(query, page);
        return Optional.of(regioes)
            .map(r -> paroquiaRepository.findAllByRegiaoInAndNameIgnoreCaseContainingOrderByName(r.getContent(), query, page))
            .orElse(Page.empty());
    }

    @Override
    public Page nextLevelChain(String query, Pageable page) {
        return regiaoChainService.get("", null);
    }
}
