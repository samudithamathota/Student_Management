package com.example.backend.modle;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@NoArgsConstructor
@AllArgsConstructor
@Data

@Document(collection = "student")
public class Student {
    @Id
    private String id;
    private String name;
    private String email;
    private String age;
    private String phone;
    private String address;
    private  String userId;

}
