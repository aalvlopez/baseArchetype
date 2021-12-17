package com.imatia.taskmanagerFS.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.imatia.taskmanagerFS.apimodel.entity.User;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("Select u from User u where u.username=?1")
    Optional<User> findByUsername(String username);

}
