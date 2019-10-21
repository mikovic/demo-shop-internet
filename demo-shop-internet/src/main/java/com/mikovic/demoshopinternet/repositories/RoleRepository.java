package com.mikovic.demoshopinternet.repositories;


import com.mikovic.demoshopinternet.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findOneByName(String theRoleName);
}
