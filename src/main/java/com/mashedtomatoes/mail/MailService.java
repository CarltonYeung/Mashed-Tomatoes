package com.mashedtomatoes.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class MailService {

    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    public MailService(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String to, String body) throws MailException {
        configure();

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("noreply@mashedtomatoes.cse308");
        msg.setTo(to);
        msg.setSubject("Verify your Mashed Tomatoes email");
        msg.setText(body);
        this.mailSender.send(msg);
    }

    private void configure() {
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
    }
}
