package com.Security.SessionSecurity;

import com.Security.SessionSecurity.Util.JksProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JksProperties.class)
@SpringBootApplication
public class TokenSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokenSecurityApplication.class, args);
	}

}
