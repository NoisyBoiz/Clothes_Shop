package com.tuananh.AdminPage.services;

import com.tuananh.AdminPage.models.dto.DataMailDto;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendHtmlMail(DataMailDto dataMail, String templateName) throws MessagingException;
}