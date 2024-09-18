package com.github.fttroy.testmailsender.model;

import lombok.Data;

@Data
public class Mail {
    private String sender;
    private String receiver;
    private String subject;
    private String text;
}
