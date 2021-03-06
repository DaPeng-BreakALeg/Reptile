package com.dapeng.reptile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

// 启用缓存功能
@EnableCaching
// 开启定时任务功能
@EnableScheduling
@SpringBootApplication
@MapperScan("com.dapeng.reptile.dao")
public class ReptileApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReptileApplication.class, args);
	}

}
