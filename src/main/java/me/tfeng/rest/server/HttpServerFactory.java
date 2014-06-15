package me.tfeng.rest.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.glassfish.grizzly.http.server.ErrorPageGenerator;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component("httpServer")
public class HttpServerFactory implements ApplicationListener<ContextClosedEvent>,
    FactoryBean<HttpServer>, InitializingBean {

  private static final Log LOG = LogFactory.getLog(HttpServerFactory.class);

  @Autowired
  private ErrorPageGenerator errorPageGenerator;

  private HttpServer httpServer;

  @Autowired
  private NetworkListener networkListener;

  /** Need to wire this in because {@link ServletRegistration#afterPropertiesSet()} should be called
   *  before this.
   */
  @Autowired
  private ServletRegistration servletRegistration;

  @Autowired
  private WebappContext webappContext;

  public void afterPropertiesSet() throws Exception {
    httpServer = new HttpServer();
    httpServer.addListener(networkListener);
    webappContext.deploy(httpServer);
    httpServer.getServerConfiguration().setJmxEnabled(true);
    httpServer.getServerConfiguration().setDefaultErrorPageGenerator(errorPageGenerator);
    httpServer.start();
    logStatus("started");
  }

  public HttpServer getObject() throws Exception {
    return httpServer;
  }

  public Class<HttpServer> getObjectType() {
    return HttpServer.class;
  }

  public boolean isSingleton() {
    return true;
  }

  private void logStatus(String status) {
    LOG.warn("Server at " + networkListener.getHost() + ":" + networkListener.getPort() + " " +
        status + ".");
  }

  public void onApplicationEvent(ContextClosedEvent event) {
    if (httpServer != null) {
      httpServer.shutdown();
      logStatus("shutdown");
    }
  }
}
