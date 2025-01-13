package com.bank.bank_projecet.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
private String f_Name;
private String l_Name;
private String phone;
private String email;
private String accountNumber;
private String address;
private String status;
private String gender;
private BigDecimal accountBalance;
@CreationTimestamp
private Date cratedDate;
@UpdateTimestamp
private Date updatedDate;




}
