package com.study.scoremanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.study.scoremanage.Mapper")
public class ScoreManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoreManageApplication.class, args);
	}

}
