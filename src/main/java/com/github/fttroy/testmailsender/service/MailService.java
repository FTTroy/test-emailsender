package com.github.fttroy.testmailsender.service;

import com.github.fttroy.testmailsender.model.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class MailService {
    Logger logger = LoggerFactory.getLogger(MailService.class);

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(Mail mail) throws MessagingException {
        logger.info("[START] - SENDING MAIL WITH BODY {}", mail);
        if (mail.getAttachments() != null && !mail.getAttachments().isEmpty()) {
            sendMailWithAttachments(mail);
        } else {
            sendSimpleMail(mail);
        }
        logger.info("[END] - SEND MAIL");
    }

    private void sendSimpleMail(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail.getSender());
        message.setTo(mail.getReceiver());
        message.setSubject(mail.getSubject());
        message.setText(mail.getText());
        mailSender.send(message);
    }

    private void sendMailWithAttachments(Mail mail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(mail.getSender());
        helper.setTo(mail.getReceiver());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getText());
        addAttachmentsToMessage(helper, mail.getAttachments());
        mailSender.send(mimeMessage);
    }

    private void addAttachmentsToMessage(MimeMessageHelper helper, List<MultipartFile> attachments) {
        attachments.forEach(attachment -> {
                    try {
                        File tempFile = Files.createTempFile("temp_", attachment.getOriginalFilename()).toFile();
                        Files.copy(attachment.getInputStream(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        helper.addAttachment(Objects.requireNonNull(attachment.getOriginalFilename()), tempFile);
                    } catch (IOException | MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}
