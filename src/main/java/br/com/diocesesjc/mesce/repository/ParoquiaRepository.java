package br.com.diocesesjc.mesce.repository;

import br.com.diocesesjc.mesce.entity.Paroquia;
import br.com.diocesesjc.mesce.entity.Regiao;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParoquiaRepository extends DataQueryFilteredRepository<Paroquia, Long> {

    Page<Paroquia> findAllByUserIdAndNameIgnoreCaseContainingOrderByName(Long userId, String name, Pageable pageable);

    Page<Paroquia> findAllByRegiaoInAndNameIgnoreCaseContainingOrderByName(List<Regiao> regiao, String name, Pageable pageable);
}
