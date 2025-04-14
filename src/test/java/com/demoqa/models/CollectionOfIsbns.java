package com.demoqa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CollectionOfIsbns {
    public CollectionOfIsbns(String isbn) {
        this.isbn = isbn;
    }
    private String isbn;
}
