package com.bank.bank_projecet.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bank.bank_projecet.dto.EmailDetails;
import com.bank.bank_projecet.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {
       try {
       SimpleMailMessage mailMessage=new SimpleMailMessage();

       mailMessage.setFrom(senderEmail);
       mailMessage.setTo(emailDetails.getRecipient());
       mailMessage.setSubject(emailDetails.getSubject());
       mailMessage.setText(emailDetails.getMessageBody());
       javaMailSender.send(mailMessage);
       log.info("THE MESSAGE SENT SUCCESSFULY");

       } catch (MailException e) {

        log.error("THE ERROR OCCURE HERE");
        throw new RuntimeException();
       }
    }

}
