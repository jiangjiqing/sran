package com.hongshen.sran_service.common;

/**
 * Created by poplar on 11/21/17.
 */
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;




        String authToken = request.getHeader("aaa");
        System.out.println(authToken);
        String method = request.getMethod();
        System.out.println(method);
        StringBuffer url = request.getRequestURL();
        System.out.println(url);
//        if (authToken.equals("qwe")){
////            String aa = "failed";
////            response.addDateHeader("aa", 1);
//            ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse)response);//转换成代理类
//            // 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理
////        chain.doFilter(request, wrapperResponse);
//            byte[] content = wrapperResponse.getContent();//获取返回值
//            ServletOutputStream out = response.getOutputStream();
//            String a ="result:failed,data:[]";
//            out.write(a.getBytes());
//            out.flush();
//            return;
//        }
//
//                if (!check(url.toString(), method, authToken)) {
//                    ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse)response);//转换成代理类
//            // 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理
////        chain.doFilter(request, wrapperResponse);
//            byte[] content = wrapperResponse.getContent();//获取返回值
//            ServletOutputStream out = response.getOutputStream();
//        String a ="result:failed,data:[]";
//        out.write(a.getBytes());
//            out.write(s.getBytes(),a.getBytes());
//            out.flush();
//            return;
//         }

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        System.out.println("*********************************过滤器被使用**************************");

//        request.getHeader("aaa");

        chain.doFilter(req, res);
    }
    public void init(FilterConfig filterConfig) {}
    public void destroy() {}
}