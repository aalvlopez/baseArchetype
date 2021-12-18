package com.imatia.taskmanagerFS.apimodel.entity.user;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@Data
@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {

    @Id
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "username") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
    private Set<RoleVO> roles;

}
