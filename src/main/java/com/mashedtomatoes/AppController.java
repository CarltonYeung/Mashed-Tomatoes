package com.mashedtomatoes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AppController {
    @Value("${application.message:Hello World}")
	private String message = "Hello World";

	@Value("${mt.title}")
	private String title = "MT!";

	@GetMapping("/")
	public String welcome() {
		return "index";
	}
}