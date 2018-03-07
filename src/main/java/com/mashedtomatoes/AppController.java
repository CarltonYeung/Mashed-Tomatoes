package com.mashedtomatoes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Controller
@EnableAutoConfiguration
public class AppController {
    @Value("${application.message:Hello World}")
	private String message = "Hello World";

	@GetMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", this.message);
		return "welcome";
	}
}
