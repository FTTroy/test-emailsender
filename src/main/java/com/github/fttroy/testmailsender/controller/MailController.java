package com.github.fttroy.testmailsender.controller;

import com.github.fttroy.testmailsender.model.Mail;
import com.github.fttroy.testmailsender.service.MailService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MailController {
    @Autowired
    private MailService service;

    @PostMapping("send-mail")
    public void sendMail(@ModelAttribute Mail mail) throws MessagingException, IOException {
        service.sendMail(mail);
    }
}
