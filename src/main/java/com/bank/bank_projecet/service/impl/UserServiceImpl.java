package com.bank.bank_projecet.service.impl;

import java.math.BigDecimal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bank.bank_projecet.dto.AccountInfo;
import com.bank.bank_projecet.dto.BankResponse;
import com.bank.bank_projecet.dto.DepositWithdrawRequest;
import com.bank.bank_projecet.dto.EmailDetails;
import com.bank.bank_projecet.dto.EnquiryRequest;
import com.bank.bank_projecet.dto.LoginDto;
import com.bank.bank_projecet.dto.TransactionDto;
import com.bank.bank_projecet.dto.TransferRequest;
import com.bank.bank_projecet.dto.UserDto;
import com.bank.bank_projecet.entity.User;
import com.bank.bank_projecet.exception.AccountNumberNotFoundException;
import com.bank.bank_projecet.exception.BalanceNotEnoughException;
import com.bank.bank_projecet.exception.DuplicatedEmailException;
import com.bank.bank_projecet.exception.TransferBalanceToItSelfException;
import com.bank.bank_projecet.exception.UserNotFoundException;
import com.bank.bank_projecet.repository.UserRepository;
import com.bank.bank_projecet.service.UserService;
import com.bank.bank_projecet.utils.AccountUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {
        private final UserRepository userRepository;
        private final EmailServiceImpl emailService;
        private final TransactionServiceImpl transactionService;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;

        @Override
        public BankResponse createNewUser(UserDto userDto) {

                if (userRepository.existsByEmail(userDto.getEmail())) {

                        throw new DuplicatedEmailException("This email already have an account");
                }

                User user = User.builder()
                                .f_Name(userDto.getF_Name())
                                .l_Name(userDto.getL_Name())
                                .phone(userDto.getPhone())
                                .email(userDto.getEmail())
                                .password(passwordEncoder.encode(userDto.getPassword()))
                                .accountNumber(AccountUtils.generateAccountNubmer())
                                .address(userDto.getAddress())
                                .status("Active")
                                .gender(userDto.getGender())
                                .accountBalance(BigDecimal.ZERO)
                                .build();

                User savedUser = userRepository.save(user);
                EmailDetails emailDetails = EmailDetails.builder()
                                .recipient(savedUser.getEmail())
                                .subject("ACCOUNT CREATION")
                                .messageBody("Congratulations! Your Account has been Created! \n"
                                                + "Your Account Details:\n"
                                                + "Account Name : " + savedUser.getF_Name() + " "
                                                + savedUser.getL_Name() + "\n"
                                                + "Account Number :" + savedUser.getAccountNumber() + "\n"
                                                + "Account Balance : " + savedUser.getAccountBalance() + "\n")
                                .build();
                emailService.sendEmailAlert(emailDetails);

                return BankResponse.builder()
                                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                                .accountInfo(AccountInfo.builder()
                                                .accountName(savedUser.getF_Name() + " " + savedUser.getL_Name())
                                                .accountNumber(savedUser.getAccountNumber())
                                                .accountBalance(savedUser.getAccountBalance())
                                                .build())
                                .build();
        }

        @Override
        public BankResponse login(LoginDto loginDto) {

                if (!userRepository.findByEmail(loginDto.getEmail()).isPresent()) {
                        throw new UserNotFoundException("Email or Password not correct");

                }

                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                loginDto.getEmail(),
                                                loginDto.getPassword()));

                EmailDetails loginAlert = EmailDetails.builder()
                                .recipient(loginDto.getEmail())
                                .subject("NEW LOGING")
                                .messageBody("YOU LOGGED IN YOUR ACCOUNT, IF YOU DON\'T MAKE THIS RWQUEST. PLEASE CONTACT WITH US")
                                .attachment(null)
                                .build();

                emailService.sendEmailAlert(loginAlert);
                User user = userRepository.findByEmail(loginDto.getEmail()).get();

                return BankResponse.builder()
                                .responseCode(AccountUtils.LOGIN_SUCCESS_MESSAGE)
                                .responseMessage(jwtService.generateToken(user))
                                .accountInfo(null)
                                .build();

        }

        @Override
        public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {

                if (!userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber())) {

                        throw new AccountNumberNotFoundException(
                                        "Account with number : " + enquiryRequest.getAccountNumber() + " NOT FOUND");
                }

                User user = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());

                return BankResponse.builder()
                                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                                .accountInfo(AccountInfo.builder()
                                                .accountBalance(user.getAccountBalance())
                                                .accountName(user.getF_Name() + " " + user.getL_Name())
                                                .accountNumber(user.getAccountNumber())
                                                .build())
                                .build();
        }

        @Override
        public String nameEnquiry(EnquiryRequest enquiryRequest) {

                if (!userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber())) {
                        throw new AccountNumberNotFoundException(
                                        "Account with number : " + enquiryRequest.getAccountNumber() + " NOT FOUND");

                }
                User user = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
                return user.getF_Name() + " " + user.getL_Name();
        }

        @Override
        public BankResponse depositAccount(DepositWithdrawRequest request) {

                if (!userRepository.existsByAccountNumber(request.getAccountNumber())) {

                        throw new AccountNumberNotFoundException(
                                        "Account with number : " + request.getAccountNumber() + " NOT FOUND");
                }
                User user = userRepository.findByAccountNumber(request.getAccountNumber());
                user.setAccountBalance(user.getAccountBalance().add(request.getAmount()));
                userRepository.save(user);

                TransactionDto transactionDto = TransactionDto.builder()
                                .accountNumber(user.getAccountNumber())
                                .amount(request.getAmount())
                                .transactionType("DEPOSIT")
                                .build();
                transactionService.saveTransaction(transactionDto);

                // Sending email alarm to user with ammount of deposit

                EmailDetails depositAlert = EmailDetails.builder()
                                .recipient(user.getEmail())
                                .subject("NEW TRANSACTION")
                                .messageBody("YOU Make a new DEPOSIT Transaction with amount : " + request.getAmount()
                                                + " ,and your account balance become : " + user.getAccountBalance())
                                .attachment(null)
                                .build();
                emailService.sendEmailAlert(depositAlert);

                return BankResponse.builder()
                                .responseCode(AccountUtils.ACCOUNT_CREDIT_CODE)
                                .responseMessage(AccountUtils.ACCOUNT_CREDIT_SUCCESS_MESSAGE)
                                .accountInfo(AccountInfo.builder()
                                                .accountName(user.getF_Name() + " " + user.getL_Name())
                                                .accountBalance(user.getAccountBalance())
                                                .accountNumber(user.getAccountNumber())
                                                .build())

                                .build();

        }

        @Override
        public BankResponse withdrawAccount(DepositWithdrawRequest request) {
                if (!userRepository.existsByAccountNumber(request.getAccountNumber())) {

                        throw new AccountNumberNotFoundException(
                                        "Account with number : " + request.getAccountNumber() + " NOT FOUND");
                }
                User user = userRepository.findByAccountNumber(request.getAccountNumber());
                if (user.getAccountBalance().compareTo(request.getAmount()) < 0) {

                        throw new BalanceNotEnoughException(
                                        "Your Balance not Enough to withdraw. the available balance amount is :"
                                                        + user.getAccountBalance().toString());
                }
                user.setAccountBalance(user.getAccountBalance().subtract(request.getAmount()));
                userRepository.save(user);
                TransactionDto transactionDto = TransactionDto.builder()
                                .accountNumber(user.getAccountNumber())
                                .amount(request.getAmount())
                                .transactionType("WITHDRAW")
                                .build();
                transactionService.saveTransaction(transactionDto);

                // Sending email alarm to user with amount of withdraw

                EmailDetails withdrawAlert = EmailDetails.builder()
                                .recipient(user.getEmail())
                                .subject("NEW TRANSACTION")
                                .messageBody("YOU Make a new WITHDRAW Transaction with amount : " + request.getAmount()
                                                + " ,and your account balance become : " + user.getAccountBalance())
                                .attachment(null)
                                .build();
                emailService.sendEmailAlert(withdrawAlert);

                return BankResponse.builder()
                                .responseCode(AccountUtils.ACCOUNT_DEBIT_CODE)
                                .responseMessage(AccountUtils.ACCOUNT_DEBIT_SUCCESS_MESSAGE)
                                .accountInfo(AccountInfo.builder()
                                                .accountName(user.getF_Name() + " " + user.getL_Name())
                                                .accountBalance(user.getAccountBalance())
                                                .accountNumber(user.getAccountNumber())
                                                .build())

                                .build();
        }

        @Transactional
        @Override
        public BankResponse transfer(TransferRequest request) {

                if (request.getSenderAccountNumber().equals(request.getReciverAcountNumber())) {
                        throw new TransferBalanceToItSelfException(
                                        "You are not allowed to transfer balance to yourself");
                }

                boolean isSenderExist = userRepository.existsByAccountNumber(request.getSenderAccountNumber());
                boolean isReciverExist = userRepository.existsByAccountNumber(request.getReciverAcountNumber());

                if (isSenderExist && !isReciverExist) {
                        throw new AccountNumberNotFoundException(
                                        "The account with number: " + request.getReciverAcountNumber() + " NOT FOUND");
                } else if (!isSenderExist && isReciverExist) {
                        throw new AccountNumberNotFoundException(
                                        "The account with number: " + request.getSenderAccountNumber() + " NOT FOUND");
                } else if (!isSenderExist && !isReciverExist) {

                        throw new AccountNumberNotFoundException("data is invalid");
                } else {

                        User senderUser = userRepository.findByAccountNumber(request.getSenderAccountNumber());
                        User reciverUser = userRepository.findByAccountNumber(request.getReciverAcountNumber());

                        if (senderUser.getAccountBalance().compareTo(request.getAmount()) < 0) {

                                throw new BalanceNotEnoughException(
                                                "Your Balance not Enough to transfer. the available balance amount is :"
                                                                + request.getAmount().toString());

                        } else {
                                senderUser.setAccountBalance(
                                                senderUser.getAccountBalance().subtract(request.getAmount()));
                                reciverUser.setAccountBalance(reciverUser.getAccountBalance().add(request.getAmount()));

                                userRepository.save(senderUser);
                                userRepository.save(reciverUser);

                                TransactionDto senderTransaction = TransactionDto.builder()
                                                .accountNumber(senderUser.getAccountNumber())
                                                .amount(request.getAmount())
                                                .transactionType("TRANSFER")
                                                .build();
                                TransactionDto reciverTransaction = TransactionDto.builder()
                                                .accountNumber(reciverUser.getAccountNumber())
                                                .amount(request.getAmount())
                                                .transactionType("RECIVE")
                                                .build();
                                transactionService.saveTransaction(senderTransaction);
                                transactionService.saveTransaction(reciverTransaction);

                                EmailDetails senderAlert = EmailDetails.builder()
                                                .recipient(senderUser.getEmail())
                                                .subject("NEW TRANSACTION")
                                                .messageBody("YOU Make a TRANSFER Transaction for account number is : "
                                                                + request.getReciverAcountNumber() + "with amount: "
                                                                + request.getAmount()
                                                                + " ,and your account balance become : "
                                                                + senderUser.getAccountBalance())
                                                .attachment(null)
                                                .build();

                                EmailDetails reciverAlert = EmailDetails.builder()
                                                .recipient(reciverUser.getEmail())
                                                .subject("NEW TRANSACTION")
                                                .messageBody("YOU Make a new RECIVE Transaction from account : "
                                                                + request.getSenderAccountNumber() + "with amount :"
                                                                + request.getAmount()
                                                                + " ,and your account balance become : "
                                                                + reciverUser.getAccountBalance())
                                                .attachment(null)
                                                .build();

                                emailService.sendEmailAlert(senderAlert);
                                emailService.sendEmailAlert(reciverAlert);

                                return BankResponse.builder()
                                                .responseCode(AccountUtils.TRANSFER_DONE_SUCCESSFULLY_CODE)
                                                .responseMessage(AccountUtils.TRANSFER_DONE_SUCCESSFULLY_MESSAGE)
                                                .accountInfo(AccountInfo.builder()
                                                                .accountName(senderUser.getF_Name() + " "
                                                                                + senderUser.getL_Name())
                                                                .accountBalance(senderUser.getAccountBalance())
                                                                .accountNumber(senderUser.getAccountNumber())
                                                                .build())
                                                .build();
                        }
                }

        }

        @Override
        public User findUserByAccountNumber(String accountNumber) {
                return userRepository.findByAccountNumber(accountNumber);
        }

}
