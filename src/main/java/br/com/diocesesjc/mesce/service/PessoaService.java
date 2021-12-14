package br.com.diocesesjc.mesce.service;

import static br.com.diocesesjc.mesce.utils.FileManager.convertToBase64;

import br.com.diocesesjc.mesce.converter.data.PessoaConverter;
import br.com.diocesesjc.mesce.dtos.request.PessoaRequest;
import br.com.diocesesjc.mesce.dtos.response.PessoaResponse;
import br.com.diocesesjc.mesce.entity.Pessoa;
import br.com.diocesesjc.mesce.entity.Usuario;
import br.com.diocesesjc.mesce.enums.RoleType;
import br.com.diocesesjc.mesce.repository.PessoaRepository;
import br.com.diocesesjc.mesce.repository.UsuarioRepository;
import br.com.diocesesjc.mesce.service.chain.PessoaChainService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PessoaService extends
    CrudService<Pessoa, PessoaRepository, PessoaConverter, PessoaRequest, PessoaResponse> {

    private final PessoaRepository pessoaRepository;
    private final PessoaConverter pessoaConverter;
    private final PessoaChainService pessoaChainService;
    private final UsuarioRepository usuarioRepository;

    public PessoaService(PessoaConverter pessoaConverter, PessoaRepository pessoaRepository, PessoaChainService pessoaChainService, UsuarioRepository usuarioRepository) {
        super(pessoaRepository, pessoaConverter);
        this.pessoaRepository = pessoaRepository;
        this.pessoaConverter = pessoaConverter;
        this.pessoaChainService = pessoaChainService;
        this.usuarioRepository = usuarioRepository;
    }

    public String loadPhoto(Long pessoaId) {
        Pessoa pessoa = pessoaRepository.getOne(pessoaId);
        return convertToBase64(pessoa.getPhotoPath());
    }

    @Override
    public Page<PessoaResponse> getDataByResource(String query, Pageable page) {
        Page<Pessoa> pessoas = pessoaChainService.get(query, page);
        return pessoaConverter.convert(pessoas);
    }

    public List<PessoaResponse> findAllWithUser() {
        List<Pessoa> pessoas = usuarioRepository.findAll().stream().map(Usuario::getPessoa).collect(Collectors.toList());
        return pessoaConverter.convert(pessoas);
    }
}
