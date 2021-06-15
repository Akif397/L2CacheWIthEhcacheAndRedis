package com.akif.demoforehcache.Repository;

import com.akif.demoforehcache.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Todo findByTodo(String todo);
}
