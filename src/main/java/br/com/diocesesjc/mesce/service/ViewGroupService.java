package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.dtos.ViewGroup;
import br.com.diocesesjc.mesce.dtos.ViewScreen;
import br.com.diocesesjc.mesce.entity.RoleScreen;
import br.com.diocesesjc.mesce.entity.Usuario;
import br.com.diocesesjc.mesce.enums.ScreenGroup;
import br.com.diocesesjc.mesce.repository.RoleScreenRepository;
import java.util.List;
import java.util.Map;
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
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<RoleScreen> roles = roleScreenRepository.findAllByRoleId(usuario.getRole().getId());
        return buildViewGroups(roles);
    }

    private List<ViewGroup> buildViewGroups(List<RoleScreen> roles) {
        return roles.stream()
            .collect(Collectors.groupingBy(RoleScreen::getScreenGroup))
            .entrySet()
            .stream()
            .map(this::buildViewGroup)
            .collect(Collectors.toList());
    }

    private ViewGroup buildViewGroup(Map.Entry<ScreenGroup, List<RoleScreen>> map) {
        return ViewGroup.builder()
            .name(map.getKey().getDescription())
            .icon(map.getKey().getIcon())
            .url(map.getKey().getUrl())
            .views(buildViews(map.getValue()))
            .build();
    }

    private List<ViewScreen> buildViews(List<RoleScreen> roles) {
        return roles.stream()
            .map(rs -> ViewScreen.builder().name(rs.getScreenType().getDescription()).url(rs.getScreenType().getPathUrl()).build())
            .collect(Collectors.toList());
    }
}
