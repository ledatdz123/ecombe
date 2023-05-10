package com.example.ecombe.payload;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Getter
@Setter
public class UserDto {
    private int userId;

    private String name;

    private String email;

    private String password;
    private String address;
    private String about;
    private String gender;
    private String phone;
    private Date date;
    private boolean active;
}
