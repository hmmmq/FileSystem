package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author meiqi
 * @since 2024-10-25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        User userById = getUserById(user.getId());
        if (userById != null && userById.getPassword().equals(user.getPassword())) {
            return userById;
        }else {
            return null;
        }
    }

    // Create a new user
    @PostMapping
    public boolean createUser(@RequestBody User user) {
        User userById = getUserById(user.getId());
        if (userById != null) {
            return false;
        }
        return userService.save(user);
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getById(id);
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.list();
    }

    // Update an existing user
    @PutMapping("/{id}")
    public boolean updateUser(@PathVariable String id, @RequestBody User user) {
        return userService.updateById(user);
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable String id) {
        return userService.removeById(id);
    }
}