package com.study.scoremanage;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan(basePackages = "com.study.scoremanage.Mapper")
class ScoreManageApplicationTests {

	@Test
	void contextLoads() {
	}

}
