package com.example.taskmanager.rest;

import com.example.taskmanager.Entity.User;
import com.example.taskmanager.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private CrudService crudService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    public UserRestController(@Qualifier("userServiceImplementation") CrudService theCrudService) {
        crudService = theCrudService;
    }

    // expose "/users" and return list of users
    @GetMapping("/users")
    public List<User> findAll() {
        return crudService.findAll();
    }

    // add mapping for GET /users/{userId}

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable int userId) {

        User theUser = (User) crudService.findById(userId);

        return theUser;
    }

    // add mapping for POST /users - add new user

    @PostMapping("/users")
    public User addUser(@RequestBody User theUser) {

        LOGGER.info("new User is added");
        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        theUser.setId(0);

        crudService.save(theUser);

        return theUser;
    }

    // add mapping for PUT /users - update existing users

    @PutMapping("/users")
    public User updateUser(@RequestBody User theUser) {

        crudService.save(theUser);

        return theUser;
    }

    // add mapping for DELETE /users/{UserId} - delete User

    @DeleteMapping("/users/{userId}")
    public String deleteEmployee(@PathVariable int userId) {
        LOGGER.info("One user is deleted");

        User tempUser = (User) crudService.findById(userId);

        // throw exception if null

        if (tempUser == null) {
            throw new NotFoundException("User id not found - " + userId);
        }

        crudService.deleteById(userId);

        return "Deleted User id - " + userId;
    }





}
