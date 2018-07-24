package com.itstyle.quartz.comet4j.constant;

import org.comet4j.core.CometConnection;
import org.comet4j.core.event.DropEvent;
import org.comet4j.core.listener.DropListener;

import javax.servlet.http.HttpServletRequest;

public class LeftListener extends DropListener {

    @Override
    public boolean handleEvent(DropEvent anEvent) {
        CometConnection conn = anEvent.getConn();

        HttpServletRequest request = conn.getRequest();
        System.out.println("LeftListener================");
/*        String webToken = "" + CookieUtil.getCookie(ComConst.COOKIE_TOKEN, request);

        Object obj = MemcacheUtil.get(webToken);*/

     /*   if(obj != null){
            Long userId = (Long)obj;
            //MemcacheUtil.deleteCache(ComConst.COMET4J_KEY + userId);

            return true;
        }else{
            return false;
        }*/
        return false;
    }
}
