package com.example.taskmanager.rest;

import com.example.taskmanager.Entity.Task;
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
public class TaskRestController {

    private CrudService crudService ,crudServiceUser;
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRestController.class);

    @Autowired
    public TaskRestController(@Qualifier("taskServiceImplementation") CrudService theCrudService
            ,@Qualifier("userServiceImplementation") CrudService theCrudServiceUser) {
        crudService = theCrudService;
        crudServiceUser =theCrudServiceUser;
    }


    // expose "/tasks" and return list of tasks
    @GetMapping("/tasks")
    public List<Task> findAll() {

        return crudService.findAll();
    }

    // add mapping for GET /tasks/{tasksId} depending on Id and make sure it exists before

    @GetMapping("/tasks/{taskId}")
    public Task getTask(@PathVariable int taskId) {

        Task theTask = (Task) crudService.findById(taskId);

        return theTask;
    }

    // add mapping for POST /tasks - add new task

    @PostMapping("/tasks/{userId}")
    public Task addTask(@RequestBody Task theTask ,@PathVariable int userId) {

        //  just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update

        LOGGER.info("new Task is added");
        theTask.setId(0);

       User theUser=(User) crudServiceUser.findById(userId);
       theTask.setUser(theUser);

        crudService.save(theTask);

        return theTask;
    }

    // add mapping for PUT /tasks - update existing task -- Ask about posting all related information

    @PutMapping("/tasks/{userId}")
    public Task updateTask(@RequestBody Task theTask,@PathVariable int userId) {

        User theUser=(User) crudServiceUser.findById(userId);
        theTask.setUser(theUser);

        crudService.save(theTask);

        return theTask;
    }

    // add mapping for DELETE /tasks/{taskId} - delete task

    @DeleteMapping("/tasks/{taskId}")
    public String deleteTask(@PathVariable int taskId) {

        LOGGER.info("one Task is deleted");
        Task tempTask = (Task) crudService.findById(taskId);

        // throw Not Found exception if null

        if (tempTask == null) {
            throw new NotFoundException("Task id not found - " + taskId);
        }

        crudService.deleteById(taskId);

        return "Deleted Task id - " + taskId;
    }





}
