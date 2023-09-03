package com.example.async;

import com.example.async.database.EmployeeDB;
import com.example.async.dto.Employee;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class SupplyAsyncDemo {

    public List<Employee> getEmployees() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Employee>> supplyAsync = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    return EmployeeDB.fetchEmployee();
                });
        return supplyAsync.get();
    }

    public List<Employee> getEmployeesWithExecutor() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(10);
        CompletableFuture<List<Employee>> supplyAsync = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    return EmployeeDB.fetchEmployee();
                }, executor);
        return supplyAsync.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SupplyAsyncDemo supplyAsyncDemo = new SupplyAsyncDemo();
        List<Employee> employees = supplyAsyncDemo.getEmployees();
        employees.forEach(System.out::println);

        supplyAsyncDemo.getEmployeesWithExecutor().stream().forEach(System.out::println);
    }
}
