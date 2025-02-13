package com.easysoftware.adapter.query;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easysoftware.common.constant.HttpConstant;
import com.easysoftware.common.utils.ResultUtil;

import jakarta.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cn.dev33.satoken.exception.NotLoginException;

import cn.dev33.satoken.stp.StpUtil;
import com.easysoftware.redis.RedisGateway;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;

@RestController
@RequestMapping("/user/")
public class UserAdapter {
    /**
     * Value injected for the cookie token name.
     */
    @Value("${cookie.token.name}")
    private String cookieTokenName;

    /**
     * Autowired redisGateway.
     */
    @Autowired
    private RedisGateway redisGateway;

    /**
     * Verify login status from oneid, and maintain session.
     *
     * @param httpServletRequest request of https.
     * @return ResponseEntity<Object>.
     */
    @RequestMapping("verifyLogin")
    public ResponseEntity<Object> doLogin(final HttpServletRequest httpServletRequest) {

        // 校验是否真正登录oneid
        // 校验cookie是否正确设置
        Cookie tokenCookie = verifyCookie(httpServletRequest);
        if (tokenCookie == null) {
            throw new NotLoginException("oneid unloggin, missing cookie", "", "");
        }
        // 校验userToken
        String userToken = httpServletRequest.getHeader(HttpConstant.TOKEN);
        if (userToken == null) {
            throw new NotLoginException("oneid unloggin, missing token", "", "");
        }

        // 用户已经真正登录oneid 以usertoken登录 并维持会话
        // 设置用户会话token
        StpUtil.login(userToken);
        redisGateway.setWithExpire(userToken, userToken, 300, TimeUnit.SECONDS);

        return ResultUtil.success(HttpStatus.OK);
    }

    /**
     * Logout and detele session.
     *
     * @param httpServletRequest request of https.
     * @return ResponseEntity<Object>.
     */
    @RequestMapping("logout")
    public ResponseEntity<Object> logout(final HttpServletRequest httpServletRequest) {
        // 用户退出 删除token信息
        String userToken = httpServletRequest.getHeader(HttpConstant.TOKEN);

        if (redisGateway.hasKey(userToken)) {
            redisGateway.detele(userToken);
        }

        StpUtil.logout();

        return ResultUtil.success(HttpStatus.OK);
    }

    /**
     * Verifies and retrieves a cookie from the HttpServletRequest.
     *
     * @param httpServletRequest The HTTP servlet request
     * @return The verified Cookie object
     */
    private Cookie verifyCookie(final HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        Cookie cookie = null;
        if (cookies != null) {
            // 获取cookie中的token
            Optional<Cookie> first = Arrays.stream(cookies).filter(c -> cookieTokenName.equals(c.getName()))
                    .findFirst();
            if (first.isPresent()) {
                cookie = first.get();
            }
        }
        return cookie;
    }

}
