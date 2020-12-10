package br.com.diocesesjc.mesce.service;

import br.com.diocesesjc.mesce.entity.Role;
import br.com.diocesesjc.mesce.enums.RoleType;
import br.com.diocesesjc.mesce.repository.RoleRepository;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InitialDataService {

    private final RoleRepository roleRepository;

    public InitialDataService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void createInitialData() {
        createRoles();
    }

    private void createRoles() {
        if (roleRepository.findAll().isEmpty()) {
            List<Role> roles = Lists.newArrayList();
            Arrays.stream(RoleType.values()).forEach(r -> roles.add(new Role(null, r, r.getDescription())));
            roleRepository.saveAll(roles);
        }
    }
}
