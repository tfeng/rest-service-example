package me.tfeng.rest.server;

import java.util.List;

import javax.servlet.Servlet;

import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;

@Component("servletRegistration")
public class ServletRegistrationFactory implements FactoryBean<ServletRegistration>,
    InitializingBean {

  @Value("${servletRegistration.class}")
  private String applicationClass;

  @Value("#{'${servletRegistration.containerRequestFilters}'.split(';')}")
  private List<String> containerRequestFilters;

  @Value("#{'${servletRegistration.containerResponseFilters}'.split(';')}")
  private List<String> containerResponseFilters;

  @Value("${servletRegistration.servletClass}")
  private Class<? extends Servlet> servletClass;

  @Value("${servletRegistration.servletName}")
  private String servletName;

  private ServletRegistration servletRegistration;

  @Autowired
  private WebappContext webappContext;

  public void afterPropertiesSet() throws Exception {
    servletRegistration = webappContext.addServlet(servletName, servletClass);
    if (containerRequestFilters != null) {
      servletRegistration.setInitParameter(
          "com.sun.jersey.spi.container.ContainerRequestFilters",
          Joiner.on(';').join(containerRequestFilters));
    }
    if (containerResponseFilters != null) {
      servletRegistration.setInitParameter(
          "com.sun.jersey.spi.container.ContainerResponseFilters",
          Joiner.on(';').join(containerResponseFilters));
    }
    servletRegistration.setInitParameter("javax.ws.rs.Application", applicationClass);
    servletRegistration.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
    servletRegistration.addMapping("/*");
  }

  public ServletRegistration getObject() throws Exception {
    return servletRegistration;
  }

  public Class<ServletRegistration> getObjectType() {
    return ServletRegistration.class;
  }

  public boolean isSingleton() {
    return true;
  }
}
