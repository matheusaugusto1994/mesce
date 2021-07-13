package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.dtos.ViewScreen;
import br.com.diocesesjc.mesce.dtos.ViewGroup;
import br.com.diocesesjc.mesce.entity.RoleScreen;
import br.com.diocesesjc.mesce.entity.Usuario;
import br.com.diocesesjc.mesce.repository.RoleScreenRepository;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ViewGroupService {

    private final RoleScreenRepository roleScreenRepository;

    public ViewGroupService(RoleScreenRepository roleScreenRepository) {
        this.roleScreenRepository = roleScreenRepository;
    }

    public List<ViewGroup> getAll() {
        return Lists.newArrayList(
            ViewGroup.builder().name("Cadastros").icon("icon-folder-alt").views(getAllRegister()).build()
        );
    }

    public List<ViewScreen> getAllRegister() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<RoleScreen> roles = roleScreenRepository.findAllByRoleId(usuario.getRole().getId());

        return roles.stream()
            .map(rs -> ViewScreen.builder().name(rs.getScreenType().getDescription()).url(rs.getScreenType().getPathUrl()).build())
            .collect(Collectors.toList());
    }
}
