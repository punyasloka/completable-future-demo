package com.example.async;

import com.example.async.dto.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RunAsyncDemo {

    public Void saveEmployee(File jsonFile) throws ExecutionException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Employee> employees = mapper.readValue(jsonFile, new TypeReference<List<Employee>>() {
                    });
                    //logic here to save the result
                    System.out.println("Thread :" + Thread.currentThread().getName());
                    employees.stream().forEach(System.out::println);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return runAsync.get();
    }

    public Void saveEmployeeWithExecutor(File jsonFile) throws ExecutionException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        Executor executor = Executors.newFixedThreadPool(10);
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Employee> employees = mapper.readValue(jsonFile, new TypeReference<List<Employee>>() {
                    });
                    //logic here to save the result
                    System.out.println("Thread :" + Thread.currentThread().getName());
                    employees.stream().forEach(System.out::println);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, executor);
        return runAsync.get();
    }

    public Void saveEmployeeWithLambda(File jsonFile) throws ExecutionException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(
                () -> {
                    try {
                        List<Employee> employees = mapper.readValue(jsonFile, new TypeReference<List<Employee>>() {
                        });
                        //logic here to save the result
                        System.out.println("Thread :" + Thread.currentThread().getName());
                        employees.stream().forEach(System.out::println);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        return runAsync.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RunAsyncDemo runAsyncDemo = new RunAsyncDemo();
        runAsyncDemo.saveEmployee(new File("employees.json"));
        runAsyncDemo.saveEmployeeWithExecutor(new File("employees.json"));
        runAsyncDemo.saveEmployeeWithLambda(new File("employees.json"));
    }
}
