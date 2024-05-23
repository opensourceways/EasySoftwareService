package com.easysoftware.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.io.File;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

@Component
public class ApplicationRunnerStart implements ApplicationRunner {

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
    public void run(ApplicationArguments args) {
        String rawSecretsDirStr = System.getenv("APPLICATION_PATH");

        if (StringUtils.isBlank(rawSecretsDirStr)) {
            LOGGER.info("deletefail, env not found");
            return;
        }

        String secretsDirStr = rawSecretsDirStr.replace("/application.yaml", "");

        File secretsDir = new File(secretsDirStr);

        if (!secretsDir.isDirectory()) {
            LOGGER.info("delete fail, not a dir");
            return;
        }

        File[] listFiles = secretsDir.listFiles();
        for (File file : listFiles) {
            if (file.delete()) {
                LOGGER.info("File deleted successfully." + file.getName());
            } else {
                LOGGER.info("Delete file failed");
            }
        }

        deleteCertFile();

        return;
    }

    /**
     * Delete cert file from obs.
     *
     */
    private void deleteCertFile() {
        String certDirStr = System.getenv("PWD").concat("/obs");

        if (StringUtils.isBlank(certDirStr)) {
            LOGGER.info("deletefail, env not found");
            return;
        }

        File certDir = new File(certDirStr);

        if (!certDir.isDirectory()) {
            LOGGER.info("delete fail, not a dir");
            return;
        }

        File[] listFiles = certDir.listFiles();
        for (File file : listFiles) {
            if (file.delete()) {
                LOGGER.info("File deleted successfully." + file.getName());
            } else {
                LOGGER.info("Delete file failed" + file.getAbsolutePath());
            }
        }
        return;
    }

}
