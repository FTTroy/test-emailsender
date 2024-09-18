package com.github.fttroy.testmailsender.controller;

import com.github.fttroy.testmailsender.model.Mail;
import com.github.fttroy.testmailsender.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @Autowired
    private MailService service;

    private final JavaMailSender mailSender;

    public MailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RequestMapping("send-mail")
    public boolean sendMail(@RequestBody Mail mail) {
        return service.sendMail(mail);
    }

    @RequestMapping("send-mail-with-attachments")
    public boolean sendMailWithAttachments(@RequestBody Mail mail) throws MessagingException {
        return service.sendMailWithAttachments(mail);
    }
}
