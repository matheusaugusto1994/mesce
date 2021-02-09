package br.com.diocesesjc.mesce.security;

import br.com.diocesesjc.mesce.entity.Usuario;
import br.com.diocesesjc.mesce.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserValidatorService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserValidatorService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findFirstByName(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("user name not found");
        }

        return usuario;
    }
}
