package com.github.fttroy.testmailsender.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class Mail {
    private String sender;
    private String receiver;
    private String subject;
    private String text;
    private List<MultipartFile> attachments;
}
