
package com.example.backend.modle;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user") // Corrected attribute name
public class User {
    @Id
    private String id;
    private String email;
    private String password;
}