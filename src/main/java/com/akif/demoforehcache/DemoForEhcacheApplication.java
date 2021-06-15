package com.akif.demoforehcache;

import com.akif.demoforehcache.scheduler.TodoScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoForEhcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoForEhcacheApplication.class, args);
        System.out.println("The code start executing");
    }

}
