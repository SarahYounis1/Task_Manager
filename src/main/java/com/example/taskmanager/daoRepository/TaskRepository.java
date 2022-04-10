package com.example.taskmanager.dao;

import com.example.taskmanager.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    //No code is needed CRUD functions already exist within JPARepository
}
