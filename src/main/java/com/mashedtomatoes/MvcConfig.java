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

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
			"classpath:/META-INF/resources/", "classpath:/resources/",
			"classpath:/static/", "classpath:/public/" };

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
  	// super.addResourceHandlers(registry);
	  registry.addResourceHandler("/**")
			  .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    registry.addResourceHandler("/files/**").addResourceLocations(filesPath);
  }
}
