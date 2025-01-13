package com.bank.bank_projecet.utils;

import java.time.Year;

public class AccountUtils {


public static final String ACCOUNT_EXISTS_CODE="001";
public static final String ACCOUNT_EXISTS_MESSAGE="THIS USER ALREADY HAVE AN ACCOUNT CREATED!";
public static final String ACCOUNT_CREATION_SUCCESS="002";
public static final String ACCOUNT_CREATION_MESSAGE="ACCOUNT HAS CREATED SUCCESSFULLY";


public static String generateAccountNubmer(){


    int min=100000;
    int max=999999;
    
    int randNumber=(int)Math.floor(Math.random()*(max-min+1)+min);

    
return String.valueOf(Year.now())+String.valueOf(randNumber);
    
}
}
