package com.bank.bank_projecet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bank_projecet.service.impl.ScheduledTasksServiceImpl;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class ScheduledController {
private final ScheduledTasksServiceImpl scheduledTasksServiceImpl;



@GetMapping("/get")
public List<String> getMethodName() {
    return scheduledTasksServiceImpl.performYourTask();
}

}
