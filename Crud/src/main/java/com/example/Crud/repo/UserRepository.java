package com.example.Crud.repo;
import com.example.Crud.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    //boolean duplicate(String name, String email,String mobile);
    default boolean duplicate(String name, String email, String mobile) {
        List<User> users = findAll();

        for (User user : users) {
            if (user.getName().equals(name) || user.getEmail().equals(email) || user.getMobile().equals(mobile)) {
                return true;
            }
        }
        return false;
    }

}
