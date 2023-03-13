package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class ExchangeServer {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ExchangeServer.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8084"));
        app.run(args);
    }
}
