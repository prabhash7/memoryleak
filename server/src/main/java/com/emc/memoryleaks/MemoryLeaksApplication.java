package com.emc.memoryleaks;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemoryLeaksApplication {
    private static final Logger logger = LoggerFactory.getLogger(MemoryLeaksApplication.class);

    MemoryLeaksApplication() {
        logger.trace("Starting MemoryLeaksApplication");
    }

    public static void main(String[] args) {
        SpringApplication.run(MemoryLeaksApplication.class, args);
    }
}
