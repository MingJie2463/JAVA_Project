package com.jayfan.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
//@EntityScan({"com.jayfan.store.domain","com.jayfan.store.web"})
public class OnlineStoreSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreSpringApplication.class, args);
	}

}