package com.easysoftware.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ServiceMap {

    /**
     * Autowired map of services with string keys.
     */
    @Autowired
    private Map<String, BaseIService> serviceMap;

    /**
     * Autowired base service.
     */
    @Autowired
    private BaseIService service;

    /**
     * Get the service based on the specified type from the service map. If not found, return the default service.
     *
     * @param type The type of service to retrieve.
     * @return The requested service or the default service if not found.
     */
    public BaseIService getIService(final String type) {
        return serviceMap.getOrDefault(type, service);
    }
}
