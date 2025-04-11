package com.demoqa.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class UserBooksResponseModel {
    String userId, username;
    ArrayList<UserBooks> books;
}
