package hsleiden.iprwc.services;

import hsleiden.iprwc.DAOs.UserDAO;
import hsleiden.iprwc.entities.ApiResponse;
import hsleiden.iprwc.entities.Role;
import hsleiden.iprwc.entities.User;
import hsleiden.iprwc.repositories.RoleRepository;
import hsleiden.iprwc.repositories.UserRepository;
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


    public ApiResponse<String> saveUser(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        user.setRoles(new ArrayList<>());

        if (this.userDAO.getUserByUsername(user.getUsername()).isPresent()) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN, null, "username already exists!");
        }

        this.userDAO.saveUserToDatabase(user);
        return new ApiResponse<>(HttpStatus.ACCEPTED, null, "user succesfully added");
    }

    public boolean getUsernameDuplicate(String username){
        List<User> users = GetUsers();
        for (User user:users){
            System.out.println(user.getUsername());
        }
        return containsName(users, username);
    }

    public boolean containsName(final List<User> list, final String name){
        return list.stream().anyMatch(o -> o.getUsername().equals(name));
    }


    public Role saveRole(Role role) {
        return userDAO.saveRoleToDatabase(role);
    }

    public ApiResponse<String> addRoleToUser(String username, String roleName) {
        if (this.gebruikerRepository.findByUsername(username).isEmpty()) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN, null, "user: " + username + " does not exist!" );
        }
        if (rolRepository.findByName(roleName).isEmpty()) {
            return new ApiResponse<>(HttpStatus.FORBIDDEN, null,"role: " + roleName + " does not exist!!" );
        }
        Role role = rolRepository.findByName(roleName).get();
        User user = gebruikerRepository.findByUsername(username).get();
        user.getRoles().add(role);
        return new ApiResponse<>(HttpStatus.ACCEPTED, null, "Role added!");
    }


    public User getUser(String username) {
        return gebruikerRepository.findByUsername(username).get();
    }

    public List<User> GetUsers() {
        return gebruikerRepository.findAll();
    }


}
