package org.greenspark404.kanbanboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
public class KanbanBoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(KanbanBoardApplication.class, args);
    }
}
