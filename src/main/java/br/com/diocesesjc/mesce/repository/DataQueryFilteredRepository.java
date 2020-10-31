package br.com.diocesesjc.mesce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DataQueryFilteredRepository<T, ID> extends CrudRepository<T, ID> {
    Page<T> findByNameIgnoreCaseContainingOrderByName(String name, Pageable pageable);
}
