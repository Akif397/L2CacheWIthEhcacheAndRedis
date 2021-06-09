package com.akif.demoforehcache.controller;

import com.akif.demoforehcache.model.Todo;
import com.akif.demoforehcache.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("item")
public class TodoController {
    @Autowired
    TodoRepository todoRepository;
    @Qualifier("redisTemplate")
    @Autowired
    RedisTemplate template;
    private final String REDIS_CACHE_HASH = "items";

    @PostMapping
    public Todo addTodo(@RequestParam(value = "id", required = true) Long id,
                        @RequestParam(value = "todo", required = true) String todo,
                        @RequestParam(value = "is_complete", required = true) boolean is_complete) {
        Todo t = new Todo();
        t.setId(id);
        t.setTodo(todo);
        t.setIs_todo_complete(is_complete);
        Todo todoFromDB = todoRepository.saveAndFlush(t);

        //now save the data to the redis server
        System.out.println("starting on saving the item in redis");
        template.opsForHash().put(REDIS_CACHE_HASH, todoFromDB.getId(), todoFromDB);
//        redisTemplate.expire("UniqueKey",60, TimeUnit.SECONDS);
        System.out.println("end saving the item in redis");

        return t;
    }

    @GetMapping
    public List<Todo> findAllTodo() {
        return todoRepository.findAll();
    }

    @Cacheable(value = "itemsCache", key = "#id")
    @GetMapping("/{id}")
    public Todo findById(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        Todo todo = null;
        Todo todoFromRedis = (Todo) template.opsForHash().get(REDIS_CACHE_HASH, idLong);
        if (todoFromRedis != null) {
            System.out.println("The item remains in redis");
        } else {
            System.out.println("The item is not in redis. So now its finding from DB");
            Optional<Todo> todo1 = todoRepository.findById(idLong);
            if (todo1.isPresent()) {
                todo = todo1.get();
            }
        }
        return todo;
    }
}
