package me.tfeng.rest.resource;

import java.util.Collections;

import javax.ws.rs.container.AsyncResponse;

import org.springframework.stereotype.Component;

@Component
public class AsyncHandler {

  public void handle(AsyncResponse response) {
    response.resume(Collections.singletonMap("synchronized", false));
  }
}
