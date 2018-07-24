package com.itstyle.quartz.config;

import com.itstyle.quartz.comet4j.CometUtil;
import org.comet4j.core.CometAppListener;
import org.comet4j.core.CometServlet;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EventListener;

/**.
 * @author 百果园
 * @date 2018端午节
 */

@Configuration
public class CometConfig{
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.setProtocol("org.apache.coyote.http11.Http11NioProtocol");
        return tomcat;
    }

    @Bean
    public ServletListenerRegistrationBean<EventListener> getCometAppListener(){
        ServletListenerRegistrationBean<EventListener> registrationBean =new ServletListenerRegistrationBean<EventListener>();
        registrationBean.setListener(new CometAppListener());
        registrationBean.setOrder(1); //加载顺序
        return registrationBean;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new CometServlet(), "/cometconn");
        //客户端代码发起连接的请求地址
        servletRegistrationBean.setAsyncSupported(true);
        servletRegistrationBean.setOrder(2);//加载顺序
        return servletRegistrationBean;
    }


    @Bean
    public CometUtil cometUtil(){
        return new CometUtil();
    }

    @Bean
    public ServletListenerRegistrationBean<EventListener> getCometListener(){
        ServletListenerRegistrationBean<EventListener> registrationBean =new ServletListenerRegistrationBean<EventListener>();
        registrationBean.setListener(cometUtil());
        registrationBean.setOrder(3);
        return registrationBean;
    }
}