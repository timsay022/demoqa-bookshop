package com.demoqa.models;

import lombok.Data;

@Data
public class AddBookRequestModel {
    String userId;
    CollectionOfIsbns collectionOfIsbns;
}
