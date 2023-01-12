package hsleiden.iprwc.DAOs;

import hsleiden.iprwc.entities.Role;
import hsleiden.iprwc.entities.User;
import hsleiden.iprwc.repositories.RoleRepository;
import hsleiden.iprwc.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDAO {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /** @author Roy van Delft
     * Constructor
     * @param userRepository takes the userrepository
     * @param roleRepository takes the rolerepository
     */
    public UserDAO(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * saves the user to the database
     * @param user takes the user to save
     * @return the saved user
     */
    public User saveUserToDatabase(User user){
        this.userRepository.save(user);

        return user;
    }

    /**
     * saves the role to the database
     * @param role takes the role to save
     * @return the saved role
     */
    public Role saveRoleToDatabase(Role role){
        this.roleRepository.save(role);

        return role;
    }

    public Optional<User> getUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String username) {
        return this.userRepository.findByEmail(username);
    }

    public void deleteUser(User user) {
        this.userRepository.delete(user);
    }
}