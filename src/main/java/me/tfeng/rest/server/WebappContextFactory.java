package me.tfeng.rest.server;

import java.util.List;

import org.glassfish.grizzly.servlet.WebappContext;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("webappContext")
public class WebappContextFactory implements FactoryBean<WebappContext>, InitializingBean {

  @Value("${webappContext.class}")
  private String contextClass;

  @Value("#{'${webappContext.listeners}'.split(';')}")
  private List<String> listeners;

  @Value("${webappContext.configLocation}")
  private String location;

  private WebappContext webappContext;

  public void afterPropertiesSet() throws Exception {
    webappContext = new WebappContext("webappContext");
    webappContext.addContextInitParameter("contextClass", contextClass);
    webappContext.addContextInitParameter("contextConfigLocation", location);
    for (String listener : listeners) {
      webappContext.addListener(listener);
    }
  }

  public WebappContext getObject() throws Exception {
    return webappContext;
  }

  public Class<WebappContext> getObjectType() {
    return WebappContext.class;
  }

  public boolean isSingleton() {
    return true;
  }
}
