package com.easysoftware.infrastructure;

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
    private FieldApplicationServiceImpl service; // 这里应该是实现类而不是接口

    @BeforeEach
    public void setUp() {  
        MockitoAnnotations.openMocks(this); // 将mock的gateway对象注入到service对象中
    } 

    @Test
    public void testQueryStat_RedisHasOpekgNum() {
        Long appNum = 100L;
        String oepkgnumString = "50";
        Long expectedOpekgNum = 50L;

        // 打桩
        when(appGateway.queryTableLength()).thenReturn(appNum);
        when(redisGateway.get(RedisConstant.DISTINCT_OPEKGNUM)).thenReturn(oepkgnumString);

        // 执行测试方法
        ResponseEntity<Object> response = service.queryStat();

        // 根据返回结果assert验证        
        Map<String, Long> resMap = (Map<String, Long>) ((ResultVo) response.getBody()).getData();  
        assertEquals(HttpStatus.SC_OK, response.getStatusCode().value());
        assertEquals(appNum, resMap.get("apppkg"));   // 其实service代码没有对appNum做任何操作，可以不加这个断言
        assertEquals(expectedOpekgNum, resMap.get("total"));

        // 除了验证结果，我们还需要验证if条件后的方法是否执行
        verify(appGateway, times(1)).queryTableLength();   // appGateway.queryTableLength()是否只被执行了刚好一次
        verify(redisGateway, times(1)).get(RedisConstant.DISTINCT_OPEKGNUM);  
        //oePkgGateway.queryTableLength()方法和redisGateway.setWithExpire()方法是否一次都没有执行
        verify(oePkgGateway, never()).queryTableLength();
        verify(redisGateway, never()).setWithExpire(anyString(), anyString(), anyLong(), any(TimeUnit.class));  

        return ;
    }

    @Test
    public void testQueryStat_RedisNotHasOpekgNum_AndGreaterThanConstant() {
        Long appNum = 100L;
        Long opekgNum = 82783L; // 这个值大于 PackageConstant.SOFTWARE_NUM
        String oepkgnumString = null;
        

        // 打桩
        when(appGateway.queryTableLength()).thenReturn(appNum);
        when(redisGateway.get(RedisConstant.DISTINCT_OPEKGNUM)).thenReturn(oepkgnumString);
        when(oePkgGateway.queryTableLength()).thenReturn(opekgNum);

        // 执行测试方法
        ResponseEntity<Object> response = service.queryStat();

        // 根据返回结果assert验证        
        Map<String, Long> resMap = (Map<String, Long>) ((ResultVo) response.getBody()).getData();  
        assertEquals(HttpStatus.SC_OK, response.getStatusCode().value());
        assertEquals(appNum, resMap.get("apppkg")); 
        assertEquals(opekgNum, resMap.get("total"));

        // 除了验证结果，我们还需要验证if条件后的方法是否执行
        verify(appGateway, times(1)).queryTableLength(); 
        verify(redisGateway, times(1)).get(RedisConstant.DISTINCT_OPEKGNUM);  
        verify(oePkgGateway, times(1)).queryTableLength();
        // redisGateway.setWithExpire确实被执行了一次
        verify(redisGateway, times(1)).setWithExpire(eq(RedisConstant.DISTINCT_OPEKGNUM), anyString(), eq(90L), eq(TimeUnit.MINUTES));  

        return ;
    }

    @Test
    public void testQueryStat_RedisNotHasOpekgNum_AndLessThanConstant() {
        Long appNum = 100L;
        Long opekgNum = 50L; // 这个值小于 PackageConstant.SOFTWARE_NUM
        String oepkgnumString = null;
        

        // 打桩
        when(appGateway.queryTableLength()).thenReturn(appNum);
        when(redisGateway.get(RedisConstant.DISTINCT_OPEKGNUM)).thenReturn(oepkgnumString);
        when(oePkgGateway.queryTableLength()).thenReturn(opekgNum);

        // 执行测试方法
        ResponseEntity<Object> response = service.queryStat();

        // 根据返回结果assert验证        
        Map<String, Long> resMap = (Map<String, Long>) ((ResultVo) response.getBody()).getData();  
        assertEquals(HttpStatus.SC_OK, response.getStatusCode().value());
        assertEquals(appNum, resMap.get("apppkg")); 
        assertEquals(opekgNum, resMap.get("total"));

        // 除了验证结果，我们还需要验证if条件后的方法是否执行
        verify(appGateway, times(1)).queryTableLength(); 
        verify(redisGateway, times(1)).get(RedisConstant.DISTINCT_OPEKGNUM);  
        verify(oePkgGateway, times(1)).queryTableLength();
        // redisGateway.setWithExpire没有被执行
        verify(redisGateway, never()).setWithExpire(anyString(), anyString(), anyLong(), any(TimeUnit.class));  

        return ;
    }
}
