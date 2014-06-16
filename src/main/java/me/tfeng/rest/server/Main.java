package me.tfeng.rest.server;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

  private static ClassPathXmlApplicationContext applicationContext;

  public static void main(String[] args) throws InterruptedException {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    applicationContext = new ClassPathXmlApplicationContext("classpath:server/*-context.xml");
    applicationContext.registerShutdownHook();

    Thread.currentThread().join();
  }
}
