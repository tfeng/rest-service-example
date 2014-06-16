package me.tfeng.rest.resource;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

@Component("jsonProvider")
public class JsonProviderFactory implements FactoryBean<JacksonJaxbJsonProvider>, InitializingBean {

  private JacksonJaxbJsonProvider provider;

  public void afterPropertiesSet() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    provider = new JacksonJaxbJsonProvider();
    provider.setMapper(mapper);
  }

  public JacksonJaxbJsonProvider getObject() throws Exception {
    return provider;
  }

  public Class<JacksonJaxbJsonProvider> getObjectType() {
    return JacksonJaxbJsonProvider.class;
  }

  public boolean isSingleton() {
    return true;
  }
}
