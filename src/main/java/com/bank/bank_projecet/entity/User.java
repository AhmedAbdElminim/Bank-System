package com.bank.bank_projecet.entity;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


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
public class User implements UserDetails{
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
private String f_Name;
private String l_Name;
private String phone;
private String email;
private String password;
private String accountNumber;
private String address;
private String status;
private String gender;
private BigDecimal accountBalance;
@CreationTimestamp
private Date cratedDate;
@UpdateTimestamp
private Date updatedDate;
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
   return List.of();
}
@Override
public String getUsername() {
    return email;
}
@Override
public String getPassword(){

    return password;
}

@Override
public boolean isAccountNonExpired() {
    return true;
}

@Override
public boolean isAccountNonLocked() {
    return true;
}

@Override
public boolean isCredentialsNonExpired() {
    return true;
}

@Override
public boolean isEnabled() {
    return true;
}






}
