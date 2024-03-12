package com.easysoftware.common.interceptor;

import java.lang.reflect.Method;
import java.security.interfaces.RSAPrivateKey;
import java.util.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.easysoftware.common.exception.AuthException;
import com.easysoftware.common.utils.HttpClientUtil;
import com.easysoftware.common.utils.ObjectMapperUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class OneidInterceptor implements HandlerInterceptor {

    @Value("${cookie.token.name}")
    private String cookieTokenName;

    // @Value("${cookie.token.domains}")
    // private String allowDomains;

    // @Value("${cookie.token.secures}")
    // private String cookieSecures;

    // @Value("${oneid.tokenBasePassword}")
    // private String oneidTokenBasePassword;

    @Value("${oneid.permissionApi}")
    private String permissionApi;

    // @Value("${rsa.authing.privateKey}")
    // private String rsaAuthingPrivateKey;

    @Value("${oneid.manage.apiBody}")
    private String manageApiBody;

    @Value("${oneid.manage.tokenApi}")
    private String manageTokenApi;

    private static final Logger logger = LoggerFactory.getLogger(OneidInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Object object) throws Exception {
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        // 检查是否有用户权限的注解
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        if (!method.isAnnotationPresent(OneidToken.class) && !method.isAnnotationPresent(CompatibleToken.class)) {
            return true;
        }
        OneidToken oneidToken = method.getAnnotation(OneidToken.class);
        CompatibleToken compatibleToken = method.getAnnotation(CompatibleToken.class);
        if ((oneidToken == null || !oneidToken.required())
                && (compatibleToken == null || !compatibleToken.required())) {
            return true;
        }

        // String headerToken = httpServletRequest.getHeader("token");
        // String headJwtTokenMd5 = verifyHeaderToken(headerToken);
        // if (StringUtils.isBlank(headJwtTokenMd5) || headJwtTokenMd5.equals("unauthorized") ) {
        //     throw new AuthException("unauthorized");
        // }

        // // 校验domain
        // String verifyDomainMsg = verifyDomain(httpServletRequest);
        // if (!verifyDomainMsg.equals("success")) {
        //     throw new AuthException(verifyDomainMsg);
        // }

        // 校验cookie
        Cookie tokenCookie = verifyCookie(httpServletRequest);
        if (tokenCookie == null) {
            throw new AuthException("unauthorized");
        }

        // // 解密cookie中加密的token
        // String token = tokenCookie.getValue();
        // try {
        //     RSAPrivateKey privateKey = RSAUtil.getPrivateKey(rsaAuthingPrivateKey);
        //     token = RSAUtil.privateDecrypt(token, privateKey);
        // } catch (Exception e) {
        //     throw new AuthException("unauthorized");
        // }

        // // 解析token
        // String userId;
        // Date issuedAt;
        // Date expiresAt;
        // String permission;
        // String verifyToken;
        // try {
        //     DecodedJWT decode = JWT.decode(token);
        //     userId = decode.getAudience().get(0);
        //     issuedAt = decode.getIssuedAt();
        //     expiresAt = decode.getExpiresAt();
        //     String permissionTemp = decode.getClaim("permission").asString();
        //     permission = new String(Base64.getDecoder().decode(permissionTemp.getBytes()));
        //     verifyToken = decode.getClaim("verifyToken").asString();
        // } catch (JWTDecodeException j) {
        //     throw new AuthException("token error");
        // }

        // // 校验token
        // String verifyTokenMsg = verifyToken(headJwtTokenMd5, token, verifyToken, userId, issuedAt, expiresAt,
        //         permission);
        // logger.info(verifyTokenMsg);
        // if (!verifyTokenMsg.equals("success")) {
        //     throw new AuthException(verifyTokenMsg);
        // }

        // 校验查看版本兼容页面的权限
        if (compatibleToken != null && compatibleToken.required()) {
            String verifyUserMsg = verifyUser(compatibleToken, httpServletRequest, tokenCookie);
            if (!verifyUserMsg.equals("success")) {
                throw new AuthException(verifyUserMsg);
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Object o, Exception e) throws Exception {
    }

    // private String verifyToken(String headerToken, String token, String verifyToken,
    //         String userId, Date issuedAt, Date expiresAt, String permission) {
    //     try {
    //         if (!headerToken.equals(verifyToken)) {
    //             return "unauthorized";
    //         }

    //         if (expiresAt.before(new Date())) {
    //             return "token expires";
    //         }

    //         // token 签名密码验证
    //         String password = permission + oneidTokenBasePassword;
    //         JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(password)).build();
    //         jwtVerifier.verify(token);

    //     } catch (Exception e) {
    //         return "unauthorized";
    //     }
    //     return "success";
    // }

    // /**
    //  * 校验header中的token
    //  *
    //  * @param headerToken header中的token
    //  * @return 校验正确返回token的MD5值
    //  */
    // private String verifyHeaderToken(String headerToken) {
    //     try {
    //         if (StringUtils.isBlank(headerToken)) {
    //             return "unauthorized";
    //         }

    //         // token 签名密码验证
    //         String password = oneidTokenBasePassword;
    //         JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(password)).build();
    //         jwtVerifier.verify(headerToken);
    //         return DigestUtils.md5DigestAsHex(headerToken.getBytes());
    //     } catch (Exception e) {
    //         logger.error("verify headertoken exception", e);
    //         return "unauthorized";
    //     }
    // }

    /**
     * 校验用户操作权限
     */
    private String verifyUser(CompatibleToken compatibleToken, HttpServletRequest httpServletRequest,
            Cookie tokenCookie) {
        try {
            if (compatibleToken != null && compatibleToken.required()) {
                List<String> pers = getUserPermission(httpServletRequest, tokenCookie);
                for (String per : pers) {
                    if (per.equalsIgnoreCase("easysoftwareread")) {
                        return "success";
                    }
                }
            }
        } catch (Exception e) {
            logger.error("verify user exception", e);
            throw new RuntimeException();
        }
        return "No permission";
    }

    @SneakyThrows
    private List<String> getUserPermission(HttpServletRequest httpServletRequest, Cookie tokenCookie) {
        String token = getManageToken();
        String userToken = httpServletRequest.getHeader("token");
        String tokenCookieValue = tokenCookie.getValue();
        String response = HttpClientUtil.getHttpClient(permissionApi, token, userToken, tokenCookieValue);
        JsonNode res = ObjectMapperUtil.toJsonNode(response);
        JsonNode permissions = res.get("data").get("permissions");
        List<String> list = new ArrayList<>();
        for (JsonNode per : permissions) {
            list.add(per.asText());
        }
        return list;
    }

    @SneakyThrows
    private String getManageToken() {
        String response = HttpClientUtil.postHttpClient(manageTokenApi, manageApiBody);
        JsonNode res = ObjectMapperUtil.toJsonNode(response);
        return res.get("token").asText();
    }

    /**
     * 获取包含存token的cookie
     *
     * @param httpServletRequest request
     * @return cookie
     */
    private Cookie verifyCookie(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        Cookie cookie = null;
        if (cookies != null) {
            // 获取cookie中的token
            Optional<Cookie> first = Arrays.stream(cookies).filter(c -> cookieTokenName.equals(c.getName()))
                    .findFirst();
            if (first.isPresent())
                cookie = first.get();
        }
        return cookie;
    }

    // /**
    //  * 校验domain
    //  *
    //  * @param httpServletRequest request
    //  * @return 是否可访问
    //  */
    // private String verifyDomain(HttpServletRequest httpServletRequest) {
    //     String referer = httpServletRequest.getHeader("referer");
    //     String origin = httpServletRequest.getHeader("origin");
    //     String[] domains = allowDomains.split(";");

    //     boolean checkReferer = checkDomain(domains, referer);
    //     boolean checkOrigin = checkDomain(domains, origin);

    //     if (!checkReferer && !checkOrigin) {
    //         return "unauthorized";
    //     }
    //     return "success";
    // }

    // private boolean checkDomain(String[] domains, String input) {
    //     if (StringUtils.isBlank(input))
    //         return true;
    //     int fromIndex;
    //     int endIndex;
    //     if (input.startsWith("http://")) {
    //         fromIndex = 7;
    //         endIndex = input.indexOf(":", fromIndex);
    //     } else {
    //         fromIndex = 8;
    //         endIndex = input.indexOf("/", fromIndex);
    //         endIndex = endIndex == -1 ? input.length() : endIndex;
    //     }
    //     String substring = input.substring(0, endIndex);
    //     for (String domain : domains) {
    //         if (substring.endsWith(domain))
    //             return true;
    //     }
    //     return false;
    // }

}