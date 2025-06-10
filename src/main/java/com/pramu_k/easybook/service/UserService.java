package com.pramu_k.easybook.service;
import com.pramu_k.easybook.model.Role;
import com.pramu_k.easybook.model.User;

import com.pramu_k.easybook.repository.RoleRepository;
import com.pramu_k.easybook.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,RoleRepository roleRepository) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User save(User registrationDto) {
        Role role = roleRepository.findByName("ROLE_CUSTOMER");
        if (role == null) {
            throw new RuntimeException("Role not found");
        }

        var user = new User(registrationDto.getFirstName(),
                registrationDto.getLastName(),
                registrationDto.getEmail(),passwordEncoder.encode(registrationDto
                .getPassword()));
        user.setRole(role);
        return userRepository.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException
                    ("Invalid username or password.");
        }
        org.springframework.security
                .core.userdetails.User user1 = new org.springframework.security
                .core.userdetails.User(user.getFirstName(),
                user.getPassword(),
                mapRoleToAuthority(user.getRole()));

        return user1;
//        return new org.springframework.security
//                .core.userdetails.User(user.getFirstName(),
//                user.getPassword(),
//                mapRoleToAuthority(user.getRole()));
    }
    private Collection<? extends GrantedAuthority> mapRoleToAuthority(Role role) {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    public List<User> getAll() {

        return userRepository.findAll();
    }

}
