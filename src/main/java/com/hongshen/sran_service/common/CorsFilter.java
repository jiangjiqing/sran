package com.hongshen.sran_service.common;

/**
 * Created by poplar on 11/21/17.
 */
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 *  跨域过滤器
 * @author meng
 * @version
 * @since 2016年6月19日
 */
@Component
public class CorsFilter extends BaseController implements Filter{

    final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CorsFilter.class);



    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        System.out.println("********************************* SRAN 过滤器被使用 **************************");

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        String authToken = request.getHeader("aaa");
        String method = request.getMethod();
        StringBuffer url = request.getRequestURL();
        String ip=request.getRemoteAddr();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (ip != null) {
            System.out.println("Visit IP:"+ip);
        }
        System.out.println("authToken = " + authToken);
        System.out.println("method = " + method);
        System.out.println(df.format(new Date()));
        System.out.println("url =" + url);

        HttpSession session = request.getSession();
        session.setAttribute("user","ooo");
        String message = "aaaaaa";
//      if (!check(url.toString(), method, authToken)) {
//          ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse)response);//转换成代理类
            // 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理
//          chain.doFilter(request, wrapperResponse);
//          byte[] content = wrapperResponse.getContent();//获取返回值
//          ServletOutputStream out = response.getOutputStream();
//          String a ="result:failed,data:[]";
//          out.write(a.getBytes());
//          out.write(s.getBytes(),a.getBytes());
//          out.flush();
//          return;
//       }

        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-Type");

//        request.getHeader("aaa");

        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}
}