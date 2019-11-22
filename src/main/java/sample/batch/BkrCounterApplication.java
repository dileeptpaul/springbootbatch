package sample.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class BkrCounterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BkrCounterApplication.class, args);
	}

}
