package org.greenspark404.kanbanboard.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {

    boolean isMailServiceEnabled();

    void send(String emailTo, String subject, String message);
}
