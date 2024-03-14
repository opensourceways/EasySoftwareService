package com.easysoftware.common.obs;

import com.obs.services.ObsClient;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectRequest;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ObsService {

    @Value("${obs.endpoint}")
    String obsEndpoint;

    @Value("${obs.bucket}")
    String obsBucketName;

    @Value("${obs.ak}")
    String obsAk;

    @Value("${obs.sk}")
    String obsSk;

    private static final Logger logger = LoggerFactory.getLogger(ObsService.class);
    private static ObsClient obsClient;

    @PostConstruct
    public void init() {
        obsClient =  new ObsClient(obsAk, obsSk, obsEndpoint);
    }

    public void putData(String ObjectKey, String filePath) {
        PutObjectRequest request = new PutObjectRequest();
        request.setBucketName(obsBucketName);
        request.setObjectKey(ObjectKey);
        request.setFile(new File(filePath));
        obsClient.putObject(request);
    }

    public InputStream getData(String ObjectKey) {
        ObsObject object = obsClient.getObject(obsBucketName, ObjectKey);
        InputStream res = object.getObjectContent();
        return res;
    }

    public String generateUrl(String name) {
        String objectKey = name + ".png";
        if(!obsClient.doesObjectExist(obsBucketName, objectKey)) {
            objectKey = "logo.png";
        }
        return "https://" + obsBucketName + "." + obsEndpoint + "/" + objectKey;
    }
}
