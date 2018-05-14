package com.mashedtomatoes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticViewController {

  @GetMapping("/about")
  public String getAbout() {
    return "base/about";
  }

  @GetMapping("/newsletter")
  public String getNewsletter() {
    return "base/newsletter";
  }

  @GetMapping("/contactus")
  public String getContactUs() { return "base/contactus"; }

  @GetMapping("/help")
  public String getHelp() { return "base/help"; }

  @GetMapping("/terms")
  public String getTerms() {
    return "base/terms";
  }
}