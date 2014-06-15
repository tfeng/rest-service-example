package me.tfeng.rest.server;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

  protected static ConfigurableApplicationContext SERVLET_CONTEXT;

  public static void main(String[] args) throws InterruptedException {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:servlet/servlet-context.xml");
    applicationContext.registerShutdownHook();
    SERVLET_CONTEXT = applicationContext;

    Thread.currentThread().join();
  }
}
