package com.bank.bank_projecet.utils;

import java.time.Year;

public class AccountUtils {


public static final String ACCOUNT_EXISTS_CODE="001";
public static final String ACCOUNT_EXISTS_MESSAGE="THIS USER ALREADY HAVE AN ACCOUNT CREATED!";
public static final String ACCOUNT_CREATION_SUCCESS="002";
public static final String ACCOUNT_CREATION_MESSAGE="ACCOUNT HAS CREATED SUCCESSFULLY";
public static final String ACCOUNT_Not_EXIST_CODE="003";
public static final String ACCOUNT_Not_EXIST_MESSAGE="User with provided account number does not exist";
public static final String ACCOUNT_FOUND_CODE="004";
public static final String ACCOUNT_FOUND_MESSAGE="User account found";
public static final String ACCOUNT_CREDIT_CODE="005";
public static final String ACCOUNT_CREDIT_SUCCESS_MESSAGE="Credit done successfully";
public static final String ACCOUNT_DEBIT_CODE="006";
public static final String ACCOUNT_DEBIT_SUCCESS_MESSAGE="Credit done successfully";
public static final String ACCOUNT_BALANCE_NOT_AVIALBLE_CODE="007";
public static final String ACCOUNT_BALANCE_NOT_AVIALBLE_MESSAGE="YOUR ACCOUNT BALANCE NOT PERMIT TO DEBIT THIS AMOUNT";
public static final String SENDER_ACCOUNT_NOT_FOUND_CODE="008";
public static final String SENDER_ACCOUNT_NOT_FOUND_MESSAGE="Sender with provided account number does not exist";
public static final String RECIVER_ACCOUNT_NOT_FOUND_CODE="009";
public static final String RECIVER_ACCOUNT_NOT_FOUND_MESSAGE="Reciver with provided account number does not exist";
public static final String SENDER_AND_RECIVER_ACCOUNT_NOT_FOUND_CODE="0010";
public static final String SENDER_AND_RECIVER_ACCOUNT_NOT_FOUND_MESSAGE="Sender and Reciver with provided account number does not exist";

public static final String TRANSFER_DONE_SUCCESSFULLY_CODE="0011";
public static final String TRANSFER_DONE_SUCCESSFULLY_MESSAGE="Your transfer Done Successfully";






public static String generateAccountNubmer(){


    int min=100000;
    int max=999999;
    
    int randNumber=(int)Math.floor(Math.random()*(max-min+1)+min);

    
return String.valueOf(Year.now())+String.valueOf(randNumber);
    
}
}
