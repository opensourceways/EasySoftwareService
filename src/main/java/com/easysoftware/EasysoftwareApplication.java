package com.easysoftware;

import com.baomidou.mybatisplus.autoconfigure.DdlApplicationRunner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.easysoftware.*"})
@MapperScan("com.easysoftware.infrastructure.mapper")
public class EasysoftwareApplication {

    /**
     * Main method for the Java application.
     *
     * @param args Command-line arguments
     */
    public static void main( final String[] args) {
        SpringApplication.run(EasysoftwareApplication.class, args);
    }

    /**
     * Bean definition for DdlApplicationRunner.
     *
     * @param ddlList List of DDLs (Data Definition Language)
     * @return An instance of DdlApplicationRunner
     */
    @Bean
    public DdlApplicationRunner ddlApplicationRunner(@Autowired(required = false) final List ddlList) {
        return new DdlApplicationRunner(ddlList);
    }
}
