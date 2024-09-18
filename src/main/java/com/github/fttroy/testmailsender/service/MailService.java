package com.github.fttroy.testmailsender.service;

import com.github.fttroy.testmailsender.model.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendMail(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail.getSender()); //anche non necessario visto che prende l'indirizzo email dalle properties
        message.setTo(mail.getReceiver());
        message.setSubject(mail.getSubject());
        message.setText(mail.getText());
        mailSender.send(message);
        return true;
    }

    public boolean sendMailWithAttachments(Mail mail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(mail.getSender()); //anche non necessario visto che prende l'indirizzo email dalle properties
        helper.setTo(mail.getReceiver());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getText());
        helper.addAttachment("RilascioJenkins.docx",new File("C:\\Users\\User\\Documents\\RilascioJenkins.docx"));
        mailSender.send(mimeMessage);
        return true;
    }
}
