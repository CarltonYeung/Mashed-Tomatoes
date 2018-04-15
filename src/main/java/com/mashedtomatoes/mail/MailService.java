package com.mashedtomatoes.mail;

import org.springframework.core.env.Environment;
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
  private Environment env; // Injection must be constructor-based
  private JavaMailSenderImpl mailSender;
  private TemplateEngine templateEngine;
  private String from;
  private String serverHost;
  private int serverPort;

  public MailService(Environment env, JavaMailSenderImpl mailSender, TemplateEngine templateEngine) {
    this.env = env;
    this.templateEngine = templateEngine;
    this.from = env.getProperty("mt.mail.from");
    this.serverHost = env.getProperty("mt.server.host");
    this.serverPort = Integer.valueOf(env.getProperty("mt.server.port"));
    configureSender(mailSender);
  }

  public void sendVerificationEmail(String to, String key) {
    String link = "http://" + serverHost + ":" + serverPort + "/verify?email=" + to + "&key=" + key;
    Context context = new Context();
    context.setVariable("link", link);
    send(to, "Verify your Mashed Tomatoes email", "emailverification", context);
  }

  public void send(String to, String subject, String templateName, Context context) {
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

  private void configureSender(JavaMailSenderImpl mailSender) {
    mailSender.setHost(env.getProperty("spring.mail.host"));
    mailSender.setPort(Integer.valueOf(env.getProperty("spring.mail.port")));
    mailSender.setUsername(env.getProperty("spring.mail.username"));
    mailSender.setPassword(env.getProperty("spring.mail.password"));
    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", env.getProperty("mt.mail.transport.protocol"));
    this.mailSender = mailSender;
  }
}
