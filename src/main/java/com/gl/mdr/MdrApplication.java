package com.gl.mdr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages= {"com.gl.mdr"})
@EnableEncryptableProperties
@EnableFeignClients
@EnableCaching
public class MdrApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MdrApplication.class, args);
	}

}