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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "First Name must not be blank")
    @NotNull(message = "First Name must not be null")
    private String f_Name;
    @NotBlank(message = "Last Name must not be blank")
    @NotNull(message = "Last Name must not be null")
    private String l_Name;
    @Pattern(regexp = "^(01)([0-2]|5)[0-9]{8}$", message = "Invalid mobile number")
    @NotBlank(message = "Phone Number must not be blank")
    @NotNull(message = "Phone Number must not be null")
    private String phone;
    @Email(message = "You Should enter a Valid email")
    @NotBlank(message = "Email must not be blank")
    @NotNull(message = "Email must not be null")
    private String email;
    @NotBlank(message = "Password must not be blank")
    @NotNull(message = "Password must not be null")
    @Size(min = 5, message = "Password must not be less than 5 char")
    private String password;
    private String accountNumber;
    @NotBlank(message = "Address must not be blank")
    @NotNull(message = "Address must not be null")
    private String address;
    private String status;
    @NotBlank(message = "Gender must not be blank")
    @NotNull(message = "Gender must not be null")
    @Pattern(regexp = "^(Male|male|Female|female)$", message = "Gender must be either 'Male' or 'Female'")
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
    public String getPassword() {

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
