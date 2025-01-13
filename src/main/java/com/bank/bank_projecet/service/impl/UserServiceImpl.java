package com.bank.bank_projecet.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.bank.bank_projecet.dto.AccountInfo;
import com.bank.bank_projecet.dto.BankResponse;
import com.bank.bank_projecet.dto.EmailDetails;
import com.bank.bank_projecet.dto.UserDto;
import com.bank.bank_projecet.entity.User;
import com.bank.bank_projecet.repository.UserRepository;
import com.bank.bank_projecet.service.UserService;
import com.bank.bank_projecet.utils.AccountUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService{
private final UserRepository userRepository;
private final EmailServiceImpl emailService;
  

    @Override
    public BankResponse createNewUser(UserDto userDto) {

        if(userRepository.existsByEmail(userDto.getEmail())){


            return BankResponse.builder()
            .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
            .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
            .accountInfo(null)
            .build();
        }
       


        User user=User.builder()
        .f_Name(userDto.getF_Name())
        .l_Name(userDto.getL_Name())
        .phone(userDto.getPhone())
        .email(userDto.getEmail())
        .accountNumber(AccountUtils.generateAccountNubmer())
        .address(userDto.getAddress())
        .status("Active")
        .gender(userDto.getGender())
        .accountBalance(BigDecimal.ZERO)
        .build();
      
      User savedUser=userRepository.save(user);
      EmailDetails emailDetails=EmailDetails.builder()
      .recipient(savedUser.getEmail())
      .subject("ACCOUNT CREATION")
      .messageBody("Congratulations! Your Account has been Created! \n"
      +"Your Account Details:\n"
      +"Account Name : "+savedUser.getF_Name()+savedUser.getL_Name()+"\n"
      +"Account Number :"+savedUser.getAccountNumber()+"\n"
      +"Account Balance : "+savedUser.getAccountBalance()+"\n"
      )
      .build();
      emailService.sendEmailAlert(emailDetails);

      return BankResponse.builder()
      .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
      .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
      .accountInfo(AccountInfo.builder()
      .accountName(savedUser.getF_Name()+" "+savedUser.getL_Name())
      .accountNumber(savedUser.getAccountNumber())
      .accountBalance(savedUser.getAccountBalance())
      .build())
      .build();
    }

}
