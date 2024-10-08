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
package com.easysoftware.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.easysoftware.application.filedapplication.FieldApplicationServiceImpl;
import com.easysoftware.common.constant.RedisConstant;
import com.easysoftware.common.entity.ResultVo;
import com.easysoftware.domain.applicationpackage.gateway.ApplicationPackageGateway;
import com.easysoftware.domain.oepackage.gateway.OEPackageGateway;
import com.easysoftware.redis.RedisGateway;

public class FieldApplicationServiceTest {

    @Mock
    private ApplicationPackageGateway appGateway;

    @Mock
    private RedisGateway redisGateway;

    @Mock
    private OEPackageGateway oePkgGateway;

    @InjectMocks
    private FieldApplicationServiceImpl service;

    @BeforeEach
    public void setUp() {  
        MockitoAnnotations.openMocks(this);
    } 

    @Test
    public void testQueryStat_RedisHasOpekgNum() {
        Long appNum = 100L;
        String oepkgnumString = "50";
        Long expectedOpekgNum = 50L;

        when(appGateway.queryTableLength()).thenReturn(appNum);
        when(redisGateway.get(RedisConstant.DISTINCT_OPEKGNUM)).thenReturn(oepkgnumString);

        ResponseEntity<Object> response = service.queryStat();
     
        Map<String, Long> resMap = (Map<String, Long>) ((ResultVo) response.getBody()).getData();  
        assertEquals(HttpStatus.SC_OK, response.getStatusCode().value());
        assertEquals(appNum, resMap.get("apppkg"));   
        assertEquals(expectedOpekgNum, resMap.get("total"));

        verify(appGateway, times(1)).queryTableLength();   
        verify(redisGateway, times(1)).get(RedisConstant.DISTINCT_OPEKGNUM);  
        verify(oePkgGateway, never()).queryTableLength();
        verify(redisGateway, never()).setWithExpire(anyString(), anyString(), anyLong(), any(TimeUnit.class));  
    }

    @Test
    public void testQueryStat_RedisNotHasOpekgNum_AndGreaterThanConstant() {
        Long appNum = 100L;
        Long opekgNum = 82783L;
        String oepkgnumString = null;

        when(appGateway.queryTableLength()).thenReturn(appNum);
        when(redisGateway.get(RedisConstant.DISTINCT_OPEKGNUM)).thenReturn(oepkgnumString);
        when(oePkgGateway.queryTableLength()).thenReturn(opekgNum);

        ResponseEntity<Object> response = service.queryStat();
    
        Map<String, Long> resMap = (Map<String, Long>) ((ResultVo) response.getBody()).getData();  
        assertEquals(HttpStatus.SC_OK, response.getStatusCode().value());
        assertEquals(appNum, resMap.get("apppkg")); 
        assertEquals(opekgNum, resMap.get("total"));

        verify(appGateway, times(1)).queryTableLength(); 
        verify(redisGateway, times(1)).get(RedisConstant.DISTINCT_OPEKGNUM);  
        verify(oePkgGateway, times(1)).queryTableLength();
        verify(redisGateway, times(1)).setWithExpire(eq(RedisConstant.DISTINCT_OPEKGNUM), anyString(), eq(90L), eq(TimeUnit.MINUTES));  
    }

    @Test
    public void testQueryStat_RedisNotHasOpekgNum_AndLessThanConstant() {
        Long appNum = 100L;
        Long opekgNum = 50L;
        String oepkgnumString = null;
        
        when(appGateway.queryTableLength()).thenReturn(appNum);
        when(redisGateway.get(RedisConstant.DISTINCT_OPEKGNUM)).thenReturn(oepkgnumString);
        when(oePkgGateway.queryTableLength()).thenReturn(opekgNum);

        ResponseEntity<Object> response = service.queryStat();
    
        Map<String, Long> resMap = (Map<String, Long>) ((ResultVo) response.getBody()).getData();  
        assertEquals(HttpStatus.SC_OK, response.getStatusCode().value());
        assertEquals(appNum, resMap.get("apppkg")); 
        assertEquals(opekgNum, resMap.get("total"));

        verify(appGateway, times(1)).queryTableLength(); 
        verify(redisGateway, times(1)).get(RedisConstant.DISTINCT_OPEKGNUM);  
        verify(oePkgGateway, times(1)).queryTableLength();
        verify(redisGateway, never()).setWithExpire(anyString(), anyString(), anyLong(), any(TimeUnit.class));  
    }
}
