package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.entity.Usuario;
import br.com.diocesesjc.mesce.repository.UsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getAll() {
        return (List<Usuario>) usuarioRepository.findAll();
    }
}
