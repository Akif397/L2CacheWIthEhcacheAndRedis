package com.akif.demoforehcache.controller;

import com.akif.demoforehcache.model.Todo;
import com.akif.demoforehcache.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
@EnableCaching
@RestController
@RequestMapping("item")
public class TodoController {
    @Autowired
    TodoRepository todoRepository;
    @Qualifier("redisTemplate")
    @Autowired
    RedisTemplate template;
    @Autowired
    CacheManager cacheManager;
    private final String REDIS_CACHE_HASH = "items";

    @PostMapping
    public Todo addTodo(@RequestBody Todo todoFromUser) {
        Todo todo = new Todo();
        todo.setTodo(todoFromUser.getTodo());
        todo.setIs_todo_complete(todoFromUser.isIs_todo_complete());
        Todo todoFromDB = todoRepository.saveAndFlush(todo);

        //now save the data to the redis server
        System.out.println("starting on saving the item in redis");
        template.opsForHash().put(REDIS_CACHE_HASH, todoFromDB.getId(), todoFromDB);
        template.expire(REDIS_CACHE_HASH, 60, TimeUnit.SECONDS);
        System.out.println("end saving the item in redis");
        System.out.println("Saving the entry in the ehcache");
        Cache cache = cacheManager.getCache("itemsCache");
        cache.put(String.valueOf(todoFromDB.getId()), todoFromDB);
        cache.get(String.valueOf(todoFromDB.getId()));

        return todo;
    }


    @Cacheable(value = "itemsCache", key = "#id")
    @GetMapping("/{id}")
    public Todo findById(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        Todo todo = null;
        Todo todoFromRedis = (Todo) template.opsForHash().get(REDIS_CACHE_HASH, idLong);
        if (todoFromRedis != null) {
            System.out.println("The item remains in redis");
            todo = todoFromRedis;
        } else {
            System.out.println("The item is not in redis. So now its finding from DB");
            Optional<Todo> todo1 = todoRepository.findById(idLong);
            if (todo1.isPresent()) {
                todo = todo1.get();
            }
        }
        return todo;
    }

    @GetMapping
    public List<Todo> findAllFromRedis() {
        Todo todo = null;
        Map<String, Todo> redisHashesMap = template.opsForHash().entries(REDIS_CACHE_HASH);

        if (redisHashesMap.size() > 0) {
            System.out.println("The items remain in redis");
            List<Todo> todoListFromRedis = new LinkedList<>();
            for (Todo todoFromRedis : redisHashesMap.values()) {
                todoListFromRedis.add(todoFromRedis);
            }
            return todoListFromRedis;
        } else {
            System.out.println("the items need to fetch from Database");
            List<Todo> todosFromDB = todoRepository.findAll();
            for (int i = 0; i < todosFromDB.size(); i++) {
                System.out.println("starting on saving the item in redis");
                template.opsForHash().put(REDIS_CACHE_HASH, todosFromDB.get(i).getId(),
                        todosFromDB.get(i));
                template.expire(REDIS_CACHE_HASH, 60, TimeUnit.SECONDS);
            }
            return todosFromDB;
        }
    }

}
