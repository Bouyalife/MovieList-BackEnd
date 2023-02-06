package com.example.MList.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.MList.Model.User;

@Repository
@CrossOrigin
public interface UserRepository extends JpaRepository<User,Integer>{
    User findByUsername(String username);

}
