package br.com.diocesesjc.mesce.repository;

import br.com.diocesesjc.mesce.entity.Pessoa;
import java.util.List;

public interface PessoaRepository extends DataQueryFilteredRepository<Pessoa, Long> {
    String findPhotoPathById(Long id);

    List<Pessoa> findAllByIdInAndOrderByName(List<Long> ids);
}
