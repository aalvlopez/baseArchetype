package com.imatia.taskmanagerFS.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.imatia.taskmanagerFS.apimodel.entity.RoleVO;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleVO, Integer> {

    @Query("select r from RoleVO r where name=?1")
    Optional<RoleVO> findByName(String name);
}
