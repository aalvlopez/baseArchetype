package com.imatia.taskmanagerFS.model.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.imatia.taskmanagerFS.apimodel.entity.user.UserVO;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@Repository
public interface UserRepository extends JpaRepository<UserVO, String> {

    @Query("Select u from UserVO u where u.username=?1")
    Optional<UserVO> findByUsername(String username);

}
