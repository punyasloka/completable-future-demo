package com.example.async.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private String empId;
    private String firstName;
    private String lastName;
    private String newJoiner;
    private String learningPending;
}
