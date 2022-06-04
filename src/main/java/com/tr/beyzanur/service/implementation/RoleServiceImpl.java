package com.tr.beyzanur.service.implementation;

import com.tr.beyzanur.exception.ServiceOperationException;
import com.tr.beyzanur.model.Role;
import com.tr.beyzanur.model.enums.RoleType;
import com.tr.beyzanur.repository.RoleRepository;
import com.tr.beyzanur.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(RoleType name) {
        Objects.requireNonNull(name, "role name cannot be null");
        return roleRepository.findByName(name).orElseThrow(() -> new
                ServiceOperationException.NotFoundException("Role not found"));
    }
}
