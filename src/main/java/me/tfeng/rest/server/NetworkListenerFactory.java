package me.tfeng.rest.server;

import org.glassfish.grizzly.http.server.NetworkListener;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("networkListener")
public class NetworkListenerFactory implements FactoryBean<NetworkListener>, InitializingBean {

  @Value("${networkListener.host}")
  private String host;

  @Value("${networkListener.name}")
  private String name;

  private NetworkListener networkListener;

  @Value("${networkListener.port}")
  private int port;

  public void afterPropertiesSet() throws Exception {
    networkListener = new NetworkListener(name, host, port);
  }

  public NetworkListener getObject() throws Exception {
    return networkListener;
  }

  public Class<NetworkListener> getObjectType() {
    return NetworkListener.class;
  }

  public boolean isSingleton() {
    return true;
  }
}
