package org.greenspark404.kanbanboard.config;

import org.greenspark404.kanbanboard.service.MailService;
import org.greenspark404.kanbanboard.service.MailServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host:}")
    private String host;

    @Value("${spring.mail.username:}")
    private String username;

    @Value("${spring.mail.password:}")
    private String password;

    @Value("${spring.mail.port:0}")
    private int port;

    @Value("${spring.mail.protocol:}")
    private String protocol;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setUsername(username);
        sender.setPassword(password);
        sender.setProtocol(protocol);
        sender.setPort(port);

        return sender;
    }

    @Bean
    public MailService mailService(JavaMailSender mailSender) {
        return new MailServiceImpl(mailSender);
    }

}
