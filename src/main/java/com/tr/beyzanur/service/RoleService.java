package com.tr.beyzanur.service;

import com.tr.beyzanur.model.Role;
import com.tr.beyzanur.model.enums.RoleType;

public interface RoleService {
    Role findByName(RoleType name);
}
