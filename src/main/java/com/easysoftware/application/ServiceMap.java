/* Copyright (c) 2024 openEuler Community
 EasySoftware is licensed under the Mulan PSL v2.
 You can use this software according to the terms and conditions of the Mulan PSL v2.
 You may obtain a copy of Mulan PSL v2 at:
     http://license.coscl.org.cn/MulanPSL2
 THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 See the Mulan PSL v2 for more details.
*/

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
