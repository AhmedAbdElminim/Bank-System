package com.bank.bank_projecet.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.bank.bank_projecet.dto.AccountInfo;
import com.bank.bank_projecet.dto.BankResponse;
import com.bank.bank_projecet.dto.CreditDebitRequest;
import com.bank.bank_projecet.dto.EmailDetails;
import com.bank.bank_projecet.dto.EnquiryRequest;
import com.bank.bank_projecet.dto.TransferRequest;
import com.bank.bank_projecet.dto.UserDto;
import com.bank.bank_projecet.entity.User;
import com.bank.bank_projecet.repository.UserRepository;
import com.bank.bank_projecet.service.UserService;
import com.bank.bank_projecet.utils.AccountUtils;

import jakarta.transaction.Transactional;
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
      +"Account Name : "+savedUser.getF_Name()+" "+savedUser.getL_Name()+"\n"
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


    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
       //check if account number is exists in database

       if(!userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber())){

        return BankResponse.builder()
        .responseCode(AccountUtils.ACCOUNT_Not_EXIST_CODE)
        .responseMessage(AccountUtils.ACCOUNT_Not_EXIST_MESSAGE)
        .accountInfo(null)
        .build();
       }
       User user=userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());


       return BankResponse.builder()
       .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
       .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
       .accountInfo(AccountInfo.builder()
       .accountBalance(user.getAccountBalance())
       .accountName(user.getF_Name()+" "+user.getL_Name())
       .accountNumber(user.getAccountNumber())
       .build())
       .build();
    }


    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {

        if(!userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber())){
            return AccountUtils.ACCOUNT_Not_EXIST_MESSAGE;
          
           }
           User user=userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
           return user.getF_Name()+" "+user.getL_Name();
    }


    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
 
        if(!userRepository.existsByAccountNumber(request.getAccountNumber())){

            return BankResponse.builder()
            .responseCode(AccountUtils.ACCOUNT_Not_EXIST_CODE)
            .responseMessage(AccountUtils.ACCOUNT_Not_EXIST_MESSAGE)
            .accountInfo(null)
            .build();
           }
           User user=userRepository.findByAccountNumber(request.getAccountNumber());
           user.setAccountBalance(user.getAccountBalance().add(request.getAmount()));
           userRepository.save(user);

           return BankResponse.builder()
           .responseCode(AccountUtils.ACCOUNT_CREDIT_CODE)
           .responseMessage(AccountUtils.ACCOUNT_CREDIT_SUCCESS_MESSAGE)
           .accountInfo(AccountInfo.builder()
           .accountName(user.getF_Name()+" "+user.getL_Name())
           .accountBalance(user.getAccountBalance())
           .accountNumber(user.getAccountNumber())
           .build())
           
           .build();

    }


    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {
        if(!userRepository.existsByAccountNumber(request.getAccountNumber())){

            return BankResponse.builder()
            .responseCode(AccountUtils.ACCOUNT_Not_EXIST_CODE)
            .responseMessage(AccountUtils.ACCOUNT_Not_EXIST_MESSAGE)
            .accountInfo(null)
            .build();
           }
           User user=userRepository.findByAccountNumber(request.getAccountNumber());
           if(user.getAccountBalance().compareTo(request.getAmount())<0){

            return BankResponse.builder()
            .responseCode(AccountUtils.ACCOUNT_BALANCE_NOT_AVIALBLE_CODE)
            .responseMessage(AccountUtils.ACCOUNT_BALANCE_NOT_AVIALBLE_MESSAGE)
            .accountInfo(AccountInfo.builder()
            .accountName(user.getF_Name()+" "+user.getL_Name())
            .accountBalance(user.getAccountBalance())
            .accountNumber(user.getAccountNumber())
            .build())
            .build();
           }
           user.setAccountBalance(user.getAccountBalance().subtract(request.getAmount()));
           userRepository.save(user);

           return BankResponse.builder()
           .responseCode(AccountUtils.ACCOUNT_DEBIT_CODE)
           .responseMessage(AccountUtils.ACCOUNT_DEBIT_SUCCESS_MESSAGE)
           .accountInfo(AccountInfo.builder()
           .accountName(user.getF_Name()+" "+user.getL_Name())
           .accountBalance(user.getAccountBalance())
           .accountNumber(user.getAccountNumber())
           .build())
           
           .build();
    }

    @Transactional
    @Override
    public BankResponse transfer(TransferRequest request) {
        
        boolean isSenderExist=userRepository.existsByAccountNumber(request.getSenderAccountNumber());
        boolean isReciverExist=userRepository.existsByAccountNumber(request.getReciverAcountNumber());

        if(isSenderExist&&!isReciverExist){
            return BankResponse.builder()
            .responseCode(AccountUtils.RECIVER_ACCOUNT_NOT_FOUND_CODE)
            .responseMessage(AccountUtils.RECIVER_ACCOUNT_NOT_FOUND_MESSAGE)
            .accountInfo(null)
            .build();
        }
        else if(!isSenderExist&&isReciverExist){
            return BankResponse.builder()
            .responseCode(AccountUtils.SENDER_ACCOUNT_NOT_FOUND_CODE)
            .responseMessage(AccountUtils.SENDER_ACCOUNT_NOT_FOUND_MESSAGE)
            .accountInfo(null)
            .build();
        }
        else if(!isSenderExist&&!isReciverExist){

            return BankResponse.builder()
            .responseCode(AccountUtils.SENDER_AND_RECIVER_ACCOUNT_NOT_FOUND_CODE)
            .responseMessage(AccountUtils.SENDER_AND_RECIVER_ACCOUNT_NOT_FOUND_MESSAGE)
            .accountInfo(null)
            .build();
        }else{
          
            User senderUser=userRepository.findByAccountNumber(request.getSenderAccountNumber());
            User reciverUser=userRepository.findByAccountNumber(request.getReciverAcountNumber());

            if(senderUser.getAccountBalance().compareTo(request.getAmount())<0){

                return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_BALANCE_NOT_AVIALBLE_CODE)
                .responseMessage(AccountUtils.ACCOUNT_BALANCE_NOT_AVIALBLE_MESSAGE)
                .accountInfo(AccountInfo.builder()
                .accountName(senderUser.getF_Name()+" "+senderUser.getL_Name())
                .accountBalance(senderUser.getAccountBalance())
                .accountNumber(senderUser.getAccountNumber())
                .build())
                .build();

            }else{


                senderUser.setAccountBalance(senderUser.getAccountBalance().subtract(request.getAmount()));
                reciverUser.setAccountBalance(reciverUser.getAccountBalance().add(request.getAmount()));

                userRepository.save(senderUser);
                userRepository.save(reciverUser);
                return BankResponse.builder()
                .responseCode(AccountUtils.TRANSFER_DONE_SUCCESSFULLY_CODE)
                .responseMessage(AccountUtils.TRANSFER_DONE_SUCCESSFULLY_MESSAGE)
                .accountInfo(AccountInfo.builder()
                .accountName(senderUser.getF_Name()+" "+senderUser.getL_Name())
                .accountBalance(senderUser.getAccountBalance())
                .accountNumber(senderUser.getAccountNumber())
                .build())
                .build();



            }








        }

    }

}
