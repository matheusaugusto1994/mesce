package br.com.diocesesjc.mesce.service.chain;

import br.com.diocesesjc.mesce.entity.Usuario;
import br.com.diocesesjc.mesce.enums.RoleType;
import br.com.diocesesjc.mesce.repository.RegiaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RegiaoChainService implements ChainService {

    private final RegiaoRepository regiaoRepository;

    public RegiaoChainService(RegiaoRepository regiaoRepository) {
        this.regiaoRepository = regiaoRepository;
    }

    @Override
    public Page get(String query, Pageable page) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (usuario.getRole().getName() == RoleType.ROLE_COORDENADOR_REGIAO ||
            usuario.getRole().getName() == RoleType.ROLE_COORDENADOR_PAROQUIAL) {
            return regiaoRepository.findAllByUserIdAndNameIgnoreCaseContainingOrderByName(usuario.getId(), query, page);
        }

        return nextLevelChain(query, page);
    }

    @Override
    public Page nextLevelChain(String query, Pageable page) {
        return Page.empty();
    }
}
