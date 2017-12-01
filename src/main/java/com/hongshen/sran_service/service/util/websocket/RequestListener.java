package com.hongshen.sran_service.service.util.websocket;

/**
 * Created by poplar on 11/28/17.
 */
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class RequestListener implements ServletRequestListener {

    public void requestInitialized(ServletRequestEvent sre)  {
        //将所有request请求都携带上httpSession
        ((HttpServletRequest) sre.getServletRequest()).getSession();

    }
    public RequestListener() {
    }

    public void requestDestroyed(ServletRequestEvent arg0)  {
    }
}
