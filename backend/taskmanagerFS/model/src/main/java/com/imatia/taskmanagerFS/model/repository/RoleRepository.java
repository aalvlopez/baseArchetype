package com.imatia.taskmanagerFS.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.imatia.taskmanagerFS.apimodel.entity.Role;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("select r from Role r where name=?1")
    Optional<Role> findByName(String name);
}
