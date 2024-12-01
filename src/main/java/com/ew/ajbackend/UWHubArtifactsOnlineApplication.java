package com.ew.ajbackend;
import com.ew.ajbackend.artifact.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class UWHubArtifactsOnlineApplication {
    public static void main(String[] args) {
        SpringApplication.run(UWHubArtifactsOnlineApplication.class, args);

        }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1, 1);
    }

}
