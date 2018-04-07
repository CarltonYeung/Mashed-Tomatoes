package com.mashedtomatoes.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.Map;


@Controller
public class AppController {
    @Value("${application.message:Hello World}")
	private String message = "Hello World";

	@Value("${mt.title}")
	private String title = "MT!";

	@GetMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", this.message);
		return "welcome";
	}
}