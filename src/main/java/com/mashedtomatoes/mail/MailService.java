package com.mashedtomatoes.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {
  @Value("${spring.mail.host}")           private String host;
  @Value("${spring.mail.port}")           private int port;
  @Value("${spring.mail.username}")       private String username;
  @Value("${spring.mail.password}")       private String password;
  @Value("${mt.mail.from}")               private String from;
  @Value("${mt.mail.transport.protocol}") private String protocol;
  @Value("${mt.server.host}")             private String serverHost;
  @Value("${mt.server.port}")             private int serverPort;

  private JavaMailSenderImpl mailSender;
  private TemplateEngine templateEngine;
  private boolean configured;

  public MailService(JavaMailSenderImpl mailSender, TemplateEngine templateEngine) {
    this.mailSender = mailSender;
    this.templateEngine = templateEngine;
    this.configured = false;
  }

  public void sendVerificationEmail(String to, String key) {
    String link = "http://" + serverHost + ":" + serverPort + "/verify?email=" + to + "&key=" + key;
    Context context = new Context();
    context.setVariable("link", link);
    send(to, "Verify your Mashed Tomatoes email", "emailverification", context);
  }

  public void send(String to, String subject, String templateName, Context context) {
    configure();
    MimeMessage mail = mailSender.createMimeMessage();
    String body = templateEngine.process(templateName, context);
    try {
      MimeMessageHelper helper = new MimeMessageHelper(mail, true);
      helper.setFrom(this.from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(body, true);
    } catch (MessagingException e) {
      System.err.println("Failed to send email.");
    }
    mailSender.send(mail);
  }

  /*
   * This method should not be called inside the constructor
   * because the necessary values will not have been injected yet.
   */
  private void configure() {
    if (!configured) {
      mailSender.setHost(host);
      mailSender.setPort(port);
      mailSender.setUsername(username);
      mailSender.setPassword(password);
      Properties props = mailSender.getJavaMailProperties();
      props.put("mail.transport.protocol", protocol);
      configured = true;
    }
  }
}
