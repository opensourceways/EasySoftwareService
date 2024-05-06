package com.easysoftware.common.obs;

import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ObsService {

    /**
     * Value injected for the OBS endpoint.
     */
    @Value("${obs.endpoint}")
    private String obsEndpoint;

    /**
     * Value injected for the OBS bucket name.
     */
    @Value("${obs.bucket}")
    private String obsBucketName;

    /**
     * Value injected for the OBS Access Key (AK).
     */
    @Value("${obs.ak}")
    private String obsAk;

    /**
     * Value injected for the OBS Secret Key (SK).
     */
    @Value("${obs.sk}")
    private String obsSk;

    /**
     * Logger instance for ObsService.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ObsService.class);

    /**
     * Static ObsClient instance for interacting with OBS.
     */
    private static ObsClient obsClient;


    /**
     * Method annotated with @PostConstruct to initialize resources.
     */
    @PostConstruct
    public void init() {
        obsClient = new ObsClient(obsAk, obsSk, obsEndpoint);
    }

    /**
     * Uploads data to OBS with the specified object key and file path.
     *
     * @param objectKey The key of the object in OBS
     * @param filePath  The local file path to upload
     */
    public void putData(final String objectKey, final String filePath) {
        PutObjectRequest request = new PutObjectRequest();
        request.setBucketName(obsBucketName);
        request.setObjectKey(objectKey);
        request.setFile(new File(filePath));
        obsClient.putObject(request);
    }

    /**
     * Generates a URL based on the provided name.
     *
     * @param name The name used to generate the URL
     * @return The generated URL
     */
    public String generateUrl(final String name) {
        String objectKey = name + ".png";
        if (!obsClient.doesObjectExist(obsBucketName, objectKey)) {
            objectKey = "logo.png";
        }
        return "https://" + obsBucketName + "." + obsEndpoint + "/" + objectKey;
    }
}
