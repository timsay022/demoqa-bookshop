package com.demoqa.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AddBookResponseModel {
    ArrayList<Books> books;
}
