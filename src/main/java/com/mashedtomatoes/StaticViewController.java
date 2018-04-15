package com.mashedtomatoes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticViewController {

    @GetMapping("/about.html")
    public String about() {
        return "base/about";
    }

    @GetMapping("/newsletter.html")
    public String newsletter() {
        return "base/newsletter";
    }
}
