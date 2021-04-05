package org.greenspark404.kanbanboard.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${mail.enabled:false}")
    private boolean mailSendEnable;

    @Value("${spring.mail.username:}")
    private String username;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean isMailServiceEnabled() {
        return mailSendEnable;
    }

    @Override
    public void send(String emailTo, String subject, String message) {
        if (mailSendEnable) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(username);
            mailMessage.setTo(emailTo);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);

            mailSender.send(mailMessage);
        }
    }
}
