
package com.easysoftware.common.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.time.Duration;

import javax.net.ssl.TrustManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;


import javax.net.ssl.SSLSocketFactory;
import java.security.cert.CertificateFactory;
import java.security.cert.Certificate;
import javax.net.ssl.SSLContext;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration  {
    private static final Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port:6379}")
    private Integer redisPort = 6379;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Value("${spring.data.redis.connect-timeout:3000}")
    private Integer redisConnectTimeout = 3000;

    @Value("${spring.data.redis.timeout:2000}")
    private Integer redisReadTimeout = 2000;

    @Value("${redis-global.caPath}")
    private String caPath;

    @Value("${spring.data.redis.poolmin-idel}")
    private Integer minIdel;

    @Value("${spring.data.redis.poolmax-idel}")
    private Integer maxIdel;
    
    @Value("${spring.data.redis.poolmax}")
    private Integer maxPool;
    


    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisClientConfiguration clientConfiguration) {

        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(redisHost);
        standaloneConfiguration.setPort(redisPort);
        standaloneConfiguration.setPassword(redisPassword);

        return new JedisConnectionFactory(standaloneConfiguration, clientConfiguration);
    }

    @Bean
    public JedisClientConfiguration clientConfiguration() throws Exception {
        JedisClientConfiguration.JedisClientConfigurationBuilder configurationBuilder
            = JedisClientConfiguration.builder()
            .connectTimeout(Duration.ofMillis(redisConnectTimeout))
            .readTimeout(Duration.ofMillis(redisReadTimeout));
    
       
        configurationBuilder.useSsl().sslSocketFactory(getTrustStoreSslSocketFactory());
        
        configurationBuilder.usePooling().poolConfig(redisPoolConfig());

        return configurationBuilder.build();
    }

    private SSLSocketFactory getTrustStoreSslSocketFactory() throws Exception{
        //加载ca证书
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate ca;
        try (InputStream is = new FileInputStream(caPath)) {
            ca = cf.generateCertificate(is);
        }catch (Exception e) {
            logger.error("redis ca load error");
            throw e;
        }

    

        //创建keystore
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        //创建TrustManager
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
            TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        //创建SSLContext
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, trustManagerFactory.getTrustManagers(), SecureRandom.getInstanceStrong());
        return context.getSocketFactory();
    }

    
    private JedisPoolConfig redisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //连接池的最小连接数
        poolConfig.setMinIdle(minIdel);
        //连接池的最大空闲连接数
        poolConfig.setMaxIdle(maxIdel);
        //连接池的最大连接数
        poolConfig.setMaxTotal(maxPool);

        return poolConfig;
    }
}