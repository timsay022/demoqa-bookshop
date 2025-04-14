package com.demoqa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AddBookRequestModel {
    String userId;
    List<CollectionOfIsbns> collectionOfIsbns;
}
