package com.demoqa.models;

import lombok.Data;

@Data
public class BooksRequestModel {
    String isbn, title, subTitle, author, publish_date, publisher, pages, description, website;
}
