package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.entity.Role;
import br.com.diocesesjc.mesce.entity.Usuario;
import br.com.diocesesjc.mesce.enums.RoleType;
import br.com.diocesesjc.mesce.repository.RoleRepository;
import br.com.diocesesjc.mesce.repository.UsuarioRepository;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InitialDataService {

    private final RoleRepository roleRepository;
    private final UsuarioRepository usuarioRepository;

    public InitialDataService(RoleRepository roleRepository, UsuarioRepository usuarioRepository) {
        this.roleRepository = roleRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void createInitialData() {
        createFirstUser();
        createRoles();
    }

    private void createFirstUser() {
        if (usuarioRepository.count() == 0) {
            Usuario usuario = Usuario.builder()
                .name("master")
                .password(new BCryptPasswordEncoder(11).encode("123"))
                .build();

            usuarioRepository.save(usuario);
        }
    }

    private void createRoles() {
        if (roleRepository.count() == 0) {
            List<Role> roles = Lists.newArrayList();
            Arrays.stream(RoleType.values())
                .forEach(r -> roles.add(Role.builder().name(r).description(r.getDescription()).build()));
            roleRepository.saveAll(roles);

            Role role = roles.stream()
                .filter(r -> r.getName() == RoleType.ROLE_ADMIN).findFirst().get();
            Usuario usuario = usuarioRepository.findFirstByName("master");
            usuario.setRole(role);
            usuarioRepository.save(usuario);
        }
    }
}
