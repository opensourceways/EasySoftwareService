package com.easysoftware;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.baomidou.mybatisplus.autoconfigure.DdlApplicationRunner;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.easysoftware.*"})
@MapperScan("com.easysoftware.infrastructure.mapper")
public class EasysoftwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasysoftwareApplication.class, args);
	}

	@Bean
 	public DdlApplicationRunner ddlApplicationRunner(@Autowired(required = false) List ddlList) {
        return new DdlApplicationRunner(ddlList);
    }
}
