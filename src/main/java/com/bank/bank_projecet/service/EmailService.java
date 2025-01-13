package com.bank.bank_projecet.service;

import com.bank.bank_projecet.dto.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);
}
