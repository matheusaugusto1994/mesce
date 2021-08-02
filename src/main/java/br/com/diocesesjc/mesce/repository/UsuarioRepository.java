package br.com.diocesesjc.mesce.repository;

import br.com.diocesesjc.mesce.entity.Pessoa;
import br.com.diocesesjc.mesce.entity.Usuario;
import br.com.diocesesjc.mesce.enums.RoleType;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends DataQueryFilteredRepository<Usuario, Long> {

    Usuario findFirstByName(String name);

    Page<Usuario> findAllByPessoaInAndNameIgnoreCaseContainingOrderByName(List<Pessoa> pessoa, String name, Pageable pageable);

    @Query("SELECT u.pessoa FROM Usuario u WHERE u.role.name = :roleType ORDER BY u.pessoa.name ")
    List<Pessoa> findAllPessoaByRoleNameOrderByPessoaName(RoleType roleType);

    @Query("SELECT u FROM Usuario u WHERE u.role.name IN (:roleTypes) ORDER BY u.name ")
    List<Usuario> findAllByRoleOrderByName(List<RoleType> roleTypes);
}
