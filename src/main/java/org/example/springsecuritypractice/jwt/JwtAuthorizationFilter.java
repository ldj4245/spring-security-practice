package org.example.springsecuritypractice.jwt;

import org.example.springsecuritypractice.user.User;
import org.example.springsecuritypractice.user.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(
            UserRepository userRepository
    ){
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        try{
            //cookie 에서 JWT token을 가져옵니다.
            token = Arrays.stream(httpServletRequest.getCookies())
                    .filter(cookie -> cookie.getName().equals(JwtProperties.COOKIE_NAME)).findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }catch (Exception ignored){

        }if(token != null){
            try{
                Authentication authentication = getUsernamePasswordAuthenticationToken(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME,null);
                cookie.setMaxAge(0);
                httpServletResponse.addCookie(cookie);
            }
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    /**
     * JWT 토큰으로 User 찾아서 UsernamePasswordAuthenticationToken을 만들어서 반환한다.
     * User가 없다면 null
     */

    private Authentication getUsernamePasswordAuthenticationToken(String token){
        String userName = JwtUtils.getUsername(token);
        if(userName != null){
            User user = userRepository.findByUsername(userName); //유저를 유저명으로 찾습니다.
            return new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()

            );
        }
        return null;
    }
}
