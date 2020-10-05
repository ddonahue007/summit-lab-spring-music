package com.redhat.summit2019.springmusic;

import com.redhat.summit2019.springmusic.repositories.TradeRepositoryPopulator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class)
			.listeners(new TradeRepositoryPopulator())
			.application()
			.run(args);
	}
}
