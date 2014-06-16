package me.tfeng.rest.resource;

import java.util.List;

import org.glassfish.hk2.api.PostConstruct;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.internal.monitoring.MonitoringFeature;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.common.base.Joiner;

public class Application extends ResourceConfig implements PostConstruct {

  @Value("${application.beanName}")
  private String beanName;

  @Value("${application.enableStatisticsMBean}")
  private boolean enableStatisticsMBean;

  @Value("${application.name}")
  private String name;

  @Value("#{'${application.packages}'.split(';')}")
  private List<String> packages;

  @Autowired
  private JacksonJaxbJsonProvider provider;

  @Override
  public void postConstruct() {
    ConfigurableApplicationContext applicationContext =
        (ConfigurableApplicationContext) ContextLoader.getCurrentWebApplicationContext();
    ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
    beanFactory.autowireBean(this);
    beanFactory.registerSingleton(beanName, this);

    setApplicationName(name);

    register(provider);
    register(RequestContextFilter.class);
    register(JacksonFeature.class);
    register(MonitoringFeature.class);

    property(ServerProperties.MONITORING_STATISTICS_MBEANS_ENABLED, enableStatisticsMBean);

    packages(Joiner.on(';').join(packages));
  }
}
