package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.entity.Pessoa;
import br.com.diocesesjc.mesce.entity.Role;
import br.com.diocesesjc.mesce.entity.RoleScreen;
import br.com.diocesesjc.mesce.entity.Usuario;
import br.com.diocesesjc.mesce.enums.RoleType;
import br.com.diocesesjc.mesce.enums.ScreenGroup;
import br.com.diocesesjc.mesce.enums.ScreenType;
import br.com.diocesesjc.mesce.repository.PessoaRepository;
import br.com.diocesesjc.mesce.repository.RoleRepository;
import br.com.diocesesjc.mesce.repository.RoleScreenRepository;
import br.com.diocesesjc.mesce.repository.UsuarioRepository;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class InitialDataService {

    private final RoleRepository roleRepository;
    private final UsuarioRepository usuarioRepository;
    private final PessoaRepository pessoaRepository;
    private final RoleScreenRepository roleScreenRepository;

    public InitialDataService(RoleRepository roleRepository, UsuarioRepository usuarioRepository,
                              PessoaRepository pessoaRepository, RoleScreenRepository roleScreenRepository) {
        this.roleRepository = roleRepository;
        this.usuarioRepository = usuarioRepository;
        this.pessoaRepository = pessoaRepository;
        this.roleScreenRepository = roleScreenRepository;
    }

    public void createInitialData() {
        createRoles();
        createRoleScreen();
        createUserAdmin();
    }

    private void createRoles() {
        if (roleRepository.count() == 0) {
            List<Role> roles = Arrays.stream(RoleType.values())
                .map(r -> Role.builder().name(r).description(r.getDescription()).build())
                .collect(Collectors.toList());
            roleRepository.saveAll(roles);
        }
    }

    private void createUserAdmin() {
        if (usuarioRepository.count() == 0) {
            Pessoa pessoa = createPessoa();
            createUserAdmin(pessoa);
        }
    }

    private void createUserAdmin(Pessoa pessoa) {
        Role roleAdmin = roleRepository.findByName(RoleType.ROLE_ADMIN);
        Usuario usuario = Usuario.builder()
            .name("master")
            .password(new BCryptPasswordEncoder(11).encode("123"))
            .pessoa(pessoa)
            .role(roleAdmin)
            .build();
        usuarioRepository.save(usuario);
    }

    private Pessoa createPessoa() {
        if (pessoaRepository.count() == 0) {
            Pessoa pessoa = Pessoa.builder()
                .name("Administrador")
                .build();

            return pessoaRepository.save(pessoa);
        }
        return null;
    }

    private void createRoleScreen() {
        if (roleScreenRepository.count() == 0) {
            List<RoleScreen> roleScreens = List.of(
                createRoleScreenAdmin(), createRoleScreenSupervisor(), createRoleScreenCoordenadorRegiao(),
                createRoleScreenCoordenadorPastoral(), createRoleScreenCoordenadorMesce(), createRoleScreenMinistro())
                .stream().flatMap(List::stream).collect(Collectors.toList());
            roleScreenRepository.saveAll(roleScreens);
        }
    }

    private List<RoleScreen> createRoleScreenAdmin() {
        Role role = roleRepository.findByName(RoleType.ROLE_ADMIN);
        return getAllRoleScreens(role);
    }

    private List<RoleScreen> createRoleScreenSupervisor() {
        Role role = roleRepository.findByName(RoleType.ROLE_SUPERVISOR);
        return getAllRoleScreens(role);
    }

    private List<RoleScreen> createRoleScreenCoordenadorRegiao() {
        Role role = roleRepository.findByName(RoleType.ROLE_COORDENADOR_REGIAO);
        return getAllRoleScreens(role);
    }

    private List<RoleScreen> getAllRoleScreens(Role role) {
        return List.of(
            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_CADASTROS).screenType(ScreenType.SCREEN_REGIAO).role(role).build(),
            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_CADASTROS).screenType(ScreenType.SCREEN_PAROQUIAS).role(role).build(),
//            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_CADASTROS).screenType(ScreenType.SCREEN_SETORES).role(role).build(),
            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_CADASTROS).screenType(ScreenType.SCREEN_PESSOAS).role(role).build(),
            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_CADASTROS).screenType(ScreenType.SCREEN_USUARIOS).role(role).build(),
            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_ACOES_RAPIDAS).screenType(ScreenType.SCREEN_ACOES_RAPIDAS).role(role).build()
        );
    }

    private List<RoleScreen> createRoleScreenCoordenadorPastoral() {
        Role role = roleRepository.findByName(RoleType.ROLE_COORDENADOR_PASTORAL);
        return List.of(
            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_CADASTROS).screenType(ScreenType.SCREEN_PAROQUIAS).role(role).build(),
//            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_CADASTROS).screenType(ScreenType.SCREEN_SETORES).role(role).build(),
            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_CADASTROS).screenType(ScreenType.SCREEN_PESSOAS).role(role).build(),
            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_CADASTROS).screenType(ScreenType.SCREEN_USUARIOS).role(role).build(),
            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_ACOES_RAPIDAS).screenType(ScreenType.SCREEN_ACOES_RAPIDAS).role(role).build()
        );
    }

    private List<RoleScreen> createRoleScreenCoordenadorMesce() {
        Role role = roleRepository.findByName(RoleType.ROLE_COORDENADOR_MESCE);
        return List.of(
//            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_CADASTROS).screenType(ScreenType.SCREEN_SETORES).role(role).build(),
            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_CADASTROS).screenType(ScreenType.SCREEN_PESSOAS).role(role).build(),
            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_CADASTROS).screenType(ScreenType.SCREEN_USUARIOS).role(role).build(),
            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_ACOES_RAPIDAS).screenType(ScreenType.SCREEN_ACOES_RAPIDAS).role(role).build()
        );
    }

    private List<RoleScreen> createRoleScreenMinistro() {
        Role role = roleRepository.findByName(RoleType.ROLE_MINISTRO);
        return List.of(
            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_CADASTROS).screenType(ScreenType.SCREEN_PESSOAS).role(role).build(),
            RoleScreen.builder().screenGroup(ScreenGroup.GROUP_CADASTROS).screenType(ScreenType.SCREEN_USUARIOS).role(role).build()
        );
    }
}
