package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.converter.PessoaParoquiaConverter;
import br.com.diocesesjc.mesce.converter.data.PessoaConverter;
import br.com.diocesesjc.mesce.dtos.PessoaParoquiaRequest;
import br.com.diocesesjc.mesce.dtos.response.PessoaParoquiaResponse;
import br.com.diocesesjc.mesce.dtos.response.PessoaResponse;
import br.com.diocesesjc.mesce.entity.Paroquia;
import br.com.diocesesjc.mesce.entity.Pessoa;
import br.com.diocesesjc.mesce.entity.PessoaParoquia;
import br.com.diocesesjc.mesce.entity.Usuario;
import br.com.diocesesjc.mesce.repository.ParoquiaRepository;
import br.com.diocesesjc.mesce.repository.PessoaParoquiaRepository;
import br.com.diocesesjc.mesce.repository.PessoaRepository;
import br.com.diocesesjc.mesce.repository.UsuarioRepository;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessoaParoquiaService {

    private final PessoaParoquiaRepository pessoaParoquiaRepository;
    private final PessoaParoquiaConverter pessoaParoquiaConverter;
    private final PessoaConverter pessoaConverter;
    private final PessoaRepository pessoaRepository;
    private final ParoquiaRepository paroquiaRepository;
    private final UsuarioRepository usuarioRepository;

    public PessoaParoquiaService(PessoaParoquiaRepository pessoaParoquiaRepository, PessoaParoquiaConverter pessoaParoquiaConverter, PessoaConverter pessoaConverter, PessoaRepository pessoaRepository, ParoquiaRepository paroquiaRepository, UsuarioRepository usuarioRepository) {
        this.pessoaParoquiaRepository = pessoaParoquiaRepository;
        this.pessoaParoquiaConverter = pessoaParoquiaConverter;
        this.pessoaConverter = pessoaConverter;
        this.pessoaRepository = pessoaRepository;
        this.paroquiaRepository = paroquiaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public PessoaParoquiaResponse getByPessoaId(Long pessoaId) {
        PessoaParoquia pessoaParoquia = pessoaParoquiaRepository.findByPessoaId(pessoaId);
        return Optional.ofNullable(pessoaParoquia)
            .map(pessoaParoquiaConverter::convert)
            .orElseGet(() -> {
                Pessoa pessoa = pessoaRepository.getOne(pessoaId);
                return pessoaParoquiaConverter.convert(pessoa);
            });
    }

    public void createOrUpdate(Paroquia paroquia) {
        Usuario usuario = getUserByParoquia(paroquia);
        PessoaParoquia pessoaParoquia = pessoaParoquiaConverter.convert(paroquia, usuario);

        createOrUpdate(pessoaParoquia);
    }

    @Transactional
    public void createOrUpdate(PessoaParoquiaRequest pessoaParoquiaRequest) {
        deleteOldPessoaParoquiaByPessoaId(pessoaParoquiaRequest.getPessoa());
        PessoaParoquia pessoaParoquia = pessoaParoquiaConverter.convert(pessoaParoquiaRequest);
        createOrUpdate(pessoaParoquia);
    }

    void createOrUpdate(PessoaParoquia pessoaParoquia) {
        pessoaParoquiaRepository.save(pessoaParoquia);
    }

    void deleteOldPessoaParoquia(Long paroquiaId) {
        Paroquia paroquia = paroquiaRepository.getOne(paroquiaId);
        Usuario oldUser = paroquia.getUser();
        if (oldUser != null) {
            pessoaParoquiaRepository.deleteByPessoaIdAndParoquiaId(paroquia.getUser().getPessoa().getId(), paroquia.getId());
        }
    }

    void deleteOldPessoaParoquiaByPessoaId(Long pessoaId) {
        pessoaParoquiaRepository.deleteAllByPessoaId(pessoaId);
    }

    private Usuario getUserByParoquia(Paroquia paroquia) {
        Usuario usuario = paroquia.getUser();
        if (usuario.getPessoa() == null) {
            usuario = usuarioRepository.getOne(paroquia.getUser().getId());
        }

        return usuario;
    }

    public List<PessoaResponse> getAllPessoaByParoquia(Long paroquiaId) {
        Page<BigInteger> pessoaIds = pessoaParoquiaRepository.findAllPessoaIdsByParoquiaAndName(List.of(paroquiaId), "%%", null);
        List <Pessoa> pessoas = pessoaRepository.findAllById(pessoaIds.getContent().stream().map(BigInteger::longValue).collect(Collectors.toList()));
        return pessoaConverter.convert(pessoas);
    }
}
