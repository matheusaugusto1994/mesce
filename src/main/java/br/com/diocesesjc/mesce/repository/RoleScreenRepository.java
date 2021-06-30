package br.com.diocesesjc.mesce.repository;

import br.com.diocesesjc.mesce.entity.RoleScreen;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleScreenRepository extends JpaRepository<RoleScreen, Long> {
    List<RoleScreen> findAllByRoleId(Long roleId);
}
