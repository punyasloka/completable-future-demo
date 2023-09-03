package com.example.async;

import com.example.async.database.EmployeeDB;
import com.example.async.dto.Employee;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class EmployeeReminderService {

    public CompletableFuture<Void> sendReminder() {
        Executor executor = Executors.newFixedThreadPool(12);
        CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Executing thread :" + Thread.currentThread().getName());
            return EmployeeDB.fetchEmployee();
        }, executor).thenApplyAsync((employees) -> {
            System.out.println("Executing thread for filter :" + Thread.currentThread().getName());
            return employees.stream()
                    .filter(employee -> "TRUE".equals(employee.getNewJoiner()))
                    .collect(Collectors.toList());
        }, executor).thenApplyAsync((employees) -> {
            System.out.println("Executing thread training pending :" + Thread.currentThread().getName());
            return employees.stream()
                    .filter(employee -> "TRUE".equals(employee.getLearningPending()))
                    .collect(Collectors.toList());

        }, executor).thenApplyAsync((employees) -> {
            System.out.println("Executing thread first name :" + Thread.currentThread().getName());
            return employees.stream().map(Employee::getFirstName).collect(Collectors.toList());
        }, executor).thenAcceptAsync((names) -> {
            System.out.println("Executing thread to print names :" + Thread.currentThread().getName());
            names.forEach(name -> printNames(name));
        });
        return completableFuture;
    }

    public static void printNames(String name) {
        System.out.println("Pending learning names " + name);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EmployeeReminderService employeeReminderService = new EmployeeReminderService();
        employeeReminderService.sendReminder().get();
    }
}
