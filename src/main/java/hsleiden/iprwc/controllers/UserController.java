package hsleiden.iprwc.controllers;

import hsleiden.iprwc.entities.ApiResponse;
import hsleiden.iprwc.entities.User;
import hsleiden.iprwc.repositories.RoleRepository;
import hsleiden.iprwc.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users" )

public class UserController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.GetUsers());
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        User user = userService.getUser(username);
        return user;
    }

    @PostMapping("/save")
    public ApiResponse<String> saveUser(@RequestBody User user) {
        ApiResponse<String> saveUserResponse = this.userService.saveUser(user);
        ApiResponse<String> addRoleResponse = this.userService.addRoleToUser(user.getUsername(), "ROLE_USER");
        if (saveUserResponse.getCode() == HttpStatus.FORBIDDEN) {
            return saveUserResponse;
        }
        if (addRoleResponse.getCode() == HttpStatus.FORBIDDEN) {
            return addRoleResponse;
        }
        return new ApiResponse<>(HttpStatus.ACCEPTED, null, "User created!");
    }

    @PostMapping("/admins/save")
    public ApiResponse<String> saveAdmin(@RequestBody User user) {
        ApiResponse<String> saveUserResponse = this.userService.saveUser(user);
        ApiResponse<String> addRoleResponse = this.userService.addRoleToUser(user.getUsername(), "ROLE_ADMIN");
        if (saveUserResponse.getCode() == HttpStatus.FORBIDDEN) {
            return saveUserResponse;
        }
        if (addRoleResponse.getCode() == HttpStatus.FORBIDDEN) {
            return addRoleResponse;
        }

        return new ApiResponse<>(HttpStatus.ACCEPTED, null,"User created!");
    }
}
