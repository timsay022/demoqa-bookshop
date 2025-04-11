package com.demoqa.models;

import lombok.Data;

@Data
public class LoginResponseModel {
    String created_date, expires, isActive, password, token, userId, username;
}
