package me.tfeng.rest.server;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.glassfish.grizzly.http.server.Request;
import org.springframework.stereotype.Component;

@Component
public class ErrorPageGenerator implements org.glassfish.grizzly.http.server.ErrorPageGenerator {

  public String generate(Request request, int status, String reasonPhrase, String description,
      Throwable exception) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    exception.printStackTrace(ps);
    ps.close();
    return baos.toString();
  }
}
