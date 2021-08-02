package br.com.diocesesjc.mesce.repository;

import br.com.diocesesjc.mesce.entity.Pessoa;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface PessoaRepository extends DataQueryFilteredRepository<Pessoa, Long> {

    @Query("SELECT p FROM Pessoa p WHERE p.id IN (:ids) ORDER BY p.name ")
    List<Pessoa> findAllByIdAndOrderByName(List<Long> ids);
}
