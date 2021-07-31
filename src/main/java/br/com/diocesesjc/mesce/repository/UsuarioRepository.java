package br.com.diocesesjc.mesce.repository;

import br.com.diocesesjc.mesce.entity.Pessoa;
import br.com.diocesesjc.mesce.entity.Usuario;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioRepository extends DataQueryFilteredRepository<Usuario, Long> {

    Usuario findFirstByName(String name);

    Page<Usuario> findAllByPessoaInAndNameIgnoreCaseContainingOrderByName(List<Pessoa> pessoa, String name, Pageable pageable);

}
