package com.easysoftware.application;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceMap {
    @Autowired
    private Map<String, BaseIService> serviceMap;

    @Autowired
    BaseIService service;

    public BaseIService getIService(String type) {
        return serviceMap.getOrDefault(type, service);
    }
}
