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
    private boolean configured;

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
        this.configured = false;
    }

    public void send(String to, String subject, String body) throws MailException {
        configure();
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("noreply@mashedtomatoes.com");
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(body);
        this.mailSender.send(msg);
    }

    private void configure() {
        if (!configured) {
            mailSender.setHost(host);
            mailSender.setPort(port);
            mailSender.setUsername(username);
            mailSender.setPassword(password);
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            configured = true;
        }
    }
}
