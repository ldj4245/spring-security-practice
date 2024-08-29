package org.example.springsecuritypractice.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class StopwatchFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        StopWatch stopWatch = new StopWatch(httpServletRequest.getServletPath());
        stopWatch.start(); //stop watch 시작
        filterChain.doFilter(httpServletRequest,httpServletResponse);
        stopWatch.stop(); // stop watch 종료
        log.info(stopWatch.shortSummary());
    }
}
