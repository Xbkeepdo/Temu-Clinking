package com.ch.clinking.interceptor;

import com.ch.clinking.service.UserService;
import com.ch.clinking.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取请求路径
        String path = request.getRequestURI();
        System.out.println("Intercepted Path: " + path);

        // 1️⃣ **放行 `/login` 和 `/register`**
        if (path.startsWith("/login") || path.startsWith("/register")) {
            return true; // 允许访问
        }

        // 2️⃣ **从 Cookie 获取 `token`**
        String token = getTokenFromCookies(request);
        System.out.println("Token from Cookie: " + token);

        if (token == null) {
            // 如果 `token` 为空，返回 401 Unauthorized 并重定向
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Token is missing.");
            response.sendRedirect("/api/user/login");
            return false;
        }

        // 3️⃣ **验证 JWT 令牌**
        Claims claims = JwtUtils.validateJWT(token).getClaims();
        if (claims == null) {
            // 如果 `token` 无效，返回 401 Unauthorized 并重定向
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid token.");
            response.sendRedirect("/api/user/login");
            return false;
        }

        // 4️⃣ **管理员权限检查**
        if (path.startsWith("/admin")) {
            String role = claims.getSubject();
            String userId = claims.getId();
            if (!"admin".equals(role) || !"-1".equals(userId)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Forbidden: Admin access required.");
                return false;
            }
        }

        // 5️⃣ **普通用户鉴权**
        return checkUser(claims, response);
    }

    // **从 Cookie 中获取 `token`**
    private String getTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // **用户合法性检查**
    private boolean checkUser(Claims claims, HttpServletResponse response) throws Exception {
        String account = claims.getId();
        String password = claims.getSubject();
        if (userService.checkLogin(account, password) == null) {
            System.out.println("User authentication failed.");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Forbidden: User authentication failed.");
            response.sendRedirect("/api/user/checkLogin");
            return false;
        }
        System.out.println("User authentication successful.");

        return true;
    }
}
