package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.data.UsuarioConverter;
import br.com.diocesesjc.mesce.dtos.request.UsuarioRequest;
import br.com.diocesesjc.mesce.dtos.response.UsuarioResponse;
import br.com.diocesesjc.mesce.entity.Usuario;
import br.com.diocesesjc.mesce.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends CrudService<Usuario, UsuarioRepository, UsuarioConverter, UsuarioRequest, UsuarioResponse> {

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioConverter usuarioConverter) {
        super(usuarioRepository, usuarioConverter);
    }
}
