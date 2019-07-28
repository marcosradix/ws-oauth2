package br.com.workmade.service;

import br.com.workmade.domain.Role;
import br.com.workmade.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findRoleByName(String name){
        return this.roleRepository.findByName(name);
    }

    public Role createRole(String name){
        return this.roleRepository.save(new Role(name));
    }
}
