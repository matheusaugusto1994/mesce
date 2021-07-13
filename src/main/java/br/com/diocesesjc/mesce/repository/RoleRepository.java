package br.com.diocesesjc.mesce.repository;

import br.com.diocesesjc.mesce.entity.Role;
import br.com.diocesesjc.mesce.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends DataQueryFilteredRepository<Role, Long> {
    Role findByName(RoleType name);
}
