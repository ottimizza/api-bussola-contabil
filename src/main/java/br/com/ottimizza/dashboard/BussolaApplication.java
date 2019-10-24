package br.com.ottimizza.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BussolaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BussolaApplication.class, args);
    }

}
