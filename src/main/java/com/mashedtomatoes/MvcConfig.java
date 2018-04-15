package com.mashedtomatoes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {
  @Value("${mt.files.path}")
  private String filesPath = "file:/tmp/";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
		    .addResourceHandler("/files/**")
		    .addResourceLocations(filesPath);
  }
}
