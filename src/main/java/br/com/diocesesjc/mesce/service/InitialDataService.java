package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.entity.Pessoa;
import br.com.diocesesjc.mesce.entity.Role;
import br.com.diocesesjc.mesce.entity.Usuario;
import br.com.diocesesjc.mesce.enums.RoleType;
import br.com.diocesesjc.mesce.repository.PessoaRepository;
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
    private final PessoaRepository pessoaRepository;

    public InitialDataService(RoleRepository roleRepository, UsuarioRepository usuarioRepository, PessoaRepository pessoaRepository) {
        this.roleRepository = roleRepository;
        this.usuarioRepository = usuarioRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public void createInitialData() {
        createFirstUser();
        createRoles();
    }

    private void createFirstUser() {
        if (usuarioRepository.count() == 0) {
            Pessoa pessoa = createFirstPessoa();
            Usuario usuario = Usuario.builder()
                .name("master")
                .password(new BCryptPasswordEncoder(11).encode("123"))
                .pessoa(pessoa)
                .build();

            usuarioRepository.save(usuario);
        }
    }

    private Pessoa createFirstPessoa() {
        if (pessoaRepository.count() == 0) {
            Pessoa pessoa = Pessoa.builder()
                .name("Administrador")
                .build();

            return pessoaRepository.save(pessoa);
        }
        return null;
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
