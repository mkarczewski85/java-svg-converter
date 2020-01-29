package com.test.uploadapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class UploadapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadapiApplication.class, args);
	}

}
