package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.data.UsuarioConverter;
import br.com.diocesesjc.mesce.dtos.request.UsuarioRequest;
import br.com.diocesesjc.mesce.dtos.response.UsuarioResponse;
import br.com.diocesesjc.mesce.entity.Usuario;
import br.com.diocesesjc.mesce.repository.UsuarioRepository;
import br.com.diocesesjc.mesce.service.chain.UsuarioChainService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends CrudService<Usuario, UsuarioRepository, UsuarioConverter, UsuarioRequest, UsuarioResponse> {

    private final UsuarioConverter usuarioConverter;
    private final UsuarioChainService usuarioChainService;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioConverter usuarioConverter, UsuarioChainService usuarioChainService) {
        super(usuarioRepository, usuarioConverter);
        this.usuarioConverter = usuarioConverter;
        this.usuarioChainService = usuarioChainService;
    }

    @Override
    public Page<UsuarioResponse> getDataByResource(String query, Pageable page) {
        Page<Usuario> usuarios = usuarioChainService.get(query, page);
        return usuarioConverter.convert(usuarios);
    }
}
