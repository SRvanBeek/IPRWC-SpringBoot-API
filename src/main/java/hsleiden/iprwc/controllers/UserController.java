package hsleiden.iprwc.controllers;

import hsleiden.iprwc.DAOs.UserDAO;
import hsleiden.iprwc.entities.ApiResponse;
import hsleiden.iprwc.entities.User;
import hsleiden.iprwc.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users" )

public class UserController {
    private final UserService userService;
    private final UserDAO userDAO;

    public UserController(UserService userService, UserDAO userDAO) {
        this.userService = userService;
        this.userDAO = userDAO;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.GetUsers());
    }

    @GetMapping("/{username}")
    public ApiResponse<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userDAO.getUserByUsername(username);
        if (user.isEmpty()) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND, null, "user does not exist!");
        }

        return new ApiResponse<>(HttpStatus.ACCEPTED, user.get(), "user received!");
    }

    @PostMapping("/register")
    public ApiResponse<String> saveUser(@RequestBody User user) {
        return this.userService.saveUser(user, false);
    }

    @PostMapping("/admins/register")
    public ApiResponse<String> saveAdmin(@RequestBody User user) {
        ApiResponse<String> saveUserResponse = this.userService.saveUser(user, true);
        if (saveUserResponse.getCode() == HttpStatus.FORBIDDEN) {
            return saveUserResponse;
        }

        return new ApiResponse<>(HttpStatus.ACCEPTED, null,"Admin created!");
    }
}
