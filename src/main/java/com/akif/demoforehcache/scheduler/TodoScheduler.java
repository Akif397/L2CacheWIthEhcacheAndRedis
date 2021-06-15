package com.akif.demoforehcache.scheduler;

import com.akif.demoforehcache.Repository.TodoRepository;
import com.akif.demoforehcache.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class TodoScheduler {
    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate template;

    @Autowired
    TodoRepository todoRepository;

    private final String REDIS_CACHE_HASH = "items";

    @Scheduled(fixedDelay = 50000)
    public void todoUpdate() {
        if (template != null && template.hasKey(REDIS_CACHE_HASH) != null) {
            Map<String, Todo> redisHashesMap = template.opsForHash().entries(REDIS_CACHE_HASH);
            if (redisHashesMap.size() > 0 || redisHashesMap != null) {
                List<Todo> todoListForDB = new LinkedList<>();

                for (Todo todoFromRedis : redisHashesMap.values()) {
                    Todo todo = todoFromRedis;
                    todoListForDB.add(todo);
                }
                for (Todo todoFromRedis : redisHashesMap.values()) {
                    template.opsForHash().delete(REDIS_CACHE_HASH, todoFromRedis.getId());
                }
                List<Todo> updatedTodoFromDB = todoRepository.saveAllAndFlush(todoListForDB);

                for (int i = 0; i < updatedTodoFromDB.size(); i++) {
                    template.opsForHash().put(REDIS_CACHE_HASH, updatedTodoFromDB.get(i).getId(),
                            updatedTodoFromDB.get(i));
                }
                System.out.println("working on update Redis : " + LocalTime.now());
            } else {
                System.out.println("working on update Redis. However in the redis template there is " +
                        "nothing : " + LocalTime.now());
            }
        } else {
            System.out.println("working on update Redis. However the redis template is not " +
                    "initialize yet : " +
                    " " + LocalTime.now());
        }
    }
}
