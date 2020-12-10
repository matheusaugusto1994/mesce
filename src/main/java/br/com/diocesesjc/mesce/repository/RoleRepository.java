package br.com.diocesesjc.mesce.repository;

import br.com.diocesesjc.mesce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends DataQueryFilteredRepository<Role, Long> {
}
