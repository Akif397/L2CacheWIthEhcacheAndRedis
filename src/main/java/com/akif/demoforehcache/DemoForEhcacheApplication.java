package com.akif.demoforehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DemoForEhcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoForEhcacheApplication.class, args);
        System.out.println("The code start executing");
    }

}
