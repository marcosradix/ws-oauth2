package br.com.workmade.cors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter  implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        resp.setHeader("Access-Control-Max-Age","3600");
        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, x-auth-token, origin, content-type, accept");
    if("OPTIONS".equalsIgnoreCase(((HttpServletRequest) request).getMethod())){
            resp.setStatus(HttpServletResponse.SC_OK);
    }else{
        chain.doFilter(request, response);
    }
    }

    @Override
    public void destroy() {

    }
}
