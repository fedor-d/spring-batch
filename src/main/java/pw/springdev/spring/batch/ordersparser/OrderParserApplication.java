package pw.springdev.spring.batch.ordersparser;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pw.springdev.spring.batch.ordersparser.helper.ConsoleArgsHelper;

@EnableBatchProcessing
@SpringBootApplication
public class OrderParserApplication {

	public static void main(String[] args) {

		SpringApplication.run(OrderParserApplication.class, ConsoleArgsHelper.getNewArgs(args));
	}
}
