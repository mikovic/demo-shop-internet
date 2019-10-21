package com.mikovic.demoshopinternet.repositories;


import com.mikovic.demoshopinternet.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findOneByUserName(String userName);
}
