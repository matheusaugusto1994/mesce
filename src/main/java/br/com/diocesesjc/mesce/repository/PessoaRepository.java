package br.com.diocesesjc.mesce.repository;

import br.com.diocesesjc.mesce.entity.Pessoa;

public interface PessoaRepository extends DataQueryFilteredRepository<Pessoa, Long> {
    String findPhotoPathById(Long id);
}
