package br.com.diocesesjc.mesce.repository;

import br.com.diocesesjc.mesce.entity.Regiao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RegiaoRepository extends DataQueryFilteredRepository<Regiao, Long> {

    Page<Regiao> findAllByUserIdAndNameIgnoreCaseContainingOrderByName(Long userId, String name, Pageable pageable);

}
