package com.imatia.taskmanagerFS.model.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.imatia.taskmanagerFS.apimodel.entity.RoleVO;
import com.imatia.taskmanagerFS.apimodel.entity.UserVO;
import com.imatia.taskmanagerFS.model.repository.RoleRepository;
import com.imatia.taskmanagerFS.model.repository.UserRepository;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UserVO> userOpt = this.userRepository.findByUsername(s);
        final UserVO user = userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found: " + s));
        return new User(user.getUsername(), user.getPassword(), this.buildSimpleGrantedAuthorities(user.getRoles()));
    }

    @PostConstruct
    private void insertDefaultUser() {
        final RoleVO adminRole = RoleVO.builder().name(RoleVO.ROLE_ADMIN).build();
        this.roleRepository.save(adminRole);
        final RoleVO userRole = RoleVO.builder().name(RoleVO.ROLE_USER).build();
        this.roleRepository.save(userRole);

        UserVO defaultadmin = UserVO.builder().username("admin").password(this.passwordEncoder.encode("pass")).roles(Collections.singleton(adminRole)).build();
        this.userRepository.save(defaultadmin);

        UserVO defaultUser = UserVO.builder().username("user").password(this.passwordEncoder.encode("pass")).roles(Collections.singleton(userRole)).build();
        this.userRepository.save(defaultUser);
    }

    private List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<RoleVO> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (RoleVO role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
