package com.crowdbotics.emptyapp1dev7170;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <h1>Application</h1>
 * 
 * <p>This is the main entry point for the application</p>
 * 
 * @author crowdbotics.com
 */
@SpringBootApplication
public class Application {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main( final String [] args ) {
		SpringApplication.run( Application.class, args );
	}
}
