package com.example.atipera;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AtiperaApplication {

    public static void main(String[] args) {
        loadEnvFile();


        SpringApplication.run(AtiperaApplication.class, args);
    }

    private static void loadEnvFile() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
    }

}
