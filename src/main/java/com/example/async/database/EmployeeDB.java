package com.example.async.database;

import com.example.async.dto.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EmployeeDB {

    public static List<Employee> fetchEmployee() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("employees.json"), new TypeReference<List<Employee>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
