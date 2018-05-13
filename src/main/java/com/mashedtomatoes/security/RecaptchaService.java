package com.mashedtomatoes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecaptchaService {

  @Autowired
  private Environment env;

  public RecaptchaService() {}

  private static final String GOOGLE_RECAPTCHA_VERIFY_URL =
          "https://www.google.com/recaptcha/api/siteverify";

  @Autowired
  private RestTemplateBuilder restTemplateBuilder;

  public boolean verifyRecaptcha(String ip, String recaptchaResponse) {
    Map<String, String> body = new HashMap<>();
    body.put("secret", env.getProperty("google.recaptcha.secret"));
    body.put("response", recaptchaResponse);
    body.put("remoteip", ip);

    ResponseEntity<Map> recaptchaResponseEntity =
            restTemplateBuilder.build()
            .postForEntity(GOOGLE_RECAPTCHA_VERIFY_URL +
            "?secret={secret}&response={response}&remoteip={remoteip}",
                    body, Map.class, body);

    Map<String, Object> responseBody = recaptchaResponseEntity.getBody();

    return (Boolean) responseBody.get("success");
  }
}
