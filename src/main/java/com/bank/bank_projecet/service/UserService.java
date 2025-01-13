package com.bank.bank_projecet.service;

import com.bank.bank_projecet.dto.BankResponse;
import com.bank.bank_projecet.dto.UserDto;

public interface UserService {

    BankResponse createNewUser(UserDto userDto);

}
