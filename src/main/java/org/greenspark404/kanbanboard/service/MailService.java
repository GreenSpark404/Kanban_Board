package org.greenspark404.kanbanboard.service;

public interface MailService {

    boolean isMailServiceEnabled();

    void send(String emailTo, String subject, String message);
}
