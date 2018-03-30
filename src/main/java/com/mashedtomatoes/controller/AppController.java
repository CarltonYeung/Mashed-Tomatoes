package com.mashedtomatoes.controller;

import java.util.Date;
import java.util.Map;

import com.mashedtomatoes.model.Audience;
import com.mashedtomatoes.repository.AudienceRepository;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


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