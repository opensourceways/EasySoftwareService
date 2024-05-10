package com.easysoftware.init;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.io.IOException;  
import java.io.File;
import java.nio.file.Files;  
import java.nio.file.Path; 

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

@Component
public class ApplicationRunnerStart implements ApplicationRunner{
	
    /**
     * Logger for ApplicationRunnerStart.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationRunnerStart.class);

    /**
     * ApplicationRunner method for the Java application.
     *
     * @param args Command-line arguments
     */
    @Override
	public void run(ApplicationArguments args){
        String secretsDirStr = System.getenv("PWD");
      
        if (StringUtils.isBlank(secretsDirStr)) {
            LOGGER.info("deletefail, env not found");
            return; 
        }
        
        File secretsDir = new File(secretsDirStr);

        if (!secretsDir.isDirectory()) {
            LOGGER.info("delete fail, not a dir");
            return; 
        }
        
        File[] listFiles = secretsDir.listFiles();
        for (File file : listFiles){
            if (file.delete()) {  
                LOGGER.info("File deleted successfully." + file.getName());  
            } else {
                LOGGER.info("Delete file failed");
            }
        }
        
        return;
	}
    
}
