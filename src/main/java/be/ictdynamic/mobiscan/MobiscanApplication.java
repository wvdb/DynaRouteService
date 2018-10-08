package be.ictdynamic.mobiscan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MobiscanApplication {
	public static void main(String[] args) {
		SpringApplication.run(MobiscanApplication.class, args);
	}
}
