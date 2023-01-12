package hsleiden.iprwc.services;

import hsleiden.iprwc.DAOs.UserDAO;
import hsleiden.iprwc.Exceptions.NotFoundException;
import hsleiden.iprwc.entities.ApiResponse;
import hsleiden.iprwc.entities.Role;
import hsleiden.iprwc.entities.User;
import hsleiden.iprwc.repositories.RoleRepository;
import hsleiden.iprwc.repositories.UserRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository gebruikerRepository;
    private final RoleRepository rolRepository;
    private final UserDAO userDAO;

    @Autowired
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserService(UserRepository gebruikerRepository, RoleRepository rolRepository, UserDAO userDAO) {
        this.gebruikerRepository = gebruikerRepository;
        this.rolRepository = rolRepository;
        this.userDAO = userDAO;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = gebruikerRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return null;
        }
        User user = userOpt.get();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


    public ApiResponse<String> saveUser(User user, boolean admin) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        user.setRoles(new ArrayList<>());

        if (this.userDAO.getUserByUsername(user.getUsername()).isPresent()) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN, null, "username already exists!");
        }
        if (this.userDAO.getUserByEmail(user.getEmail()).isPresent()) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN, null, "email already in use!");
        }

        try {
            user = addRoleToUser(user, "ROLE_USER");
            if (admin) {
                user = addRoleToUser(user, "ROLE_Admin");
            }
        }
        catch (NotFoundException e) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN, null, e.getMessage());
        }
        this.userDAO.saveUserToDatabase(user);
        return new ApiResponse<>(HttpStatus.ACCEPTED, null, "user succesfully added");
    }


    public Role saveRole(Role role) {
        return userDAO.saveRoleToDatabase(role);
    }

    private User addRoleToUser(User user, String roleName) {
        if (rolRepository.findByName(roleName).isEmpty()) {
            throw new NotFoundException("role: " + roleName + " does not exist!");
        }

        Role role = rolRepository.findByName(roleName).get();
        user.getRoles().add(role);
        return user;
    }


    public User getUser(String username) {
        return gebruikerRepository.findByUsername(username).get();
    }

    public List<User> GetUsers() {
        return gebruikerRepository.findAll();
    }


}
