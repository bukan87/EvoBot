package ru.evotor.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

/**
 * @author a.ilyin
 */
@SpringBootApplication
public class EvotorBotApp {

    public static void main(String[] args){
        ApiContextInitializer.init();
        SpringApplication.run(EvotorBotApp.class, args);
    }
}