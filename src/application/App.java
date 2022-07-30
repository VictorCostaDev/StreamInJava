package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Employee;

public class App {
    public static void main(String[] args) {
        
        Locale.setDefault(Locale.US);
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter full file path: ");
        String path = scan.next();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            
            List<Employee> employees = new ArrayList<>();

            String line = br.readLine();

            while(line != null) {
                String[] fields = line.split(",");
                String name = fields[0];
                String email = fields[1];
                double salary = Double.parseDouble(fields[2]);
                Employee employee = new Employee(name, email, salary);
                employees.add(employee);
                line = br.readLine();
            }

            System.out.print("Enter salary: ");
            double salary = scan.nextDouble();

            List<String> employeesHighSalary = employees.stream()
                                    .filter(emp -> emp.getSalary() > salary)
                                    .map(emp -> emp.getEmail())
                                    .sorted((email1,email2) -> email1.toUpperCase().compareTo(email2.toUpperCase()))
                                    .collect(Collectors.toList());
            
            System.out.println("Email of people whose salary is more than 2000.00:");
            employeesHighSalary.forEach(System.out::println);

            double sumNamesBeginM = employees.stream()
                                .filter(emp -> emp.getName().charAt(0) == 'M')
                                .map(emp -> emp.getSalary())
                                .reduce(0.0, (subtotal, element) -> subtotal + element);

            System.out.println("Sum of salary of people whose name starts with 'M': " + String.format("%.2f", sumNamesBeginM));
            
        } catch (IOException error) {
            System.out.println("Error: " + error.getMessage());
        }

        scan.close();
    }
}
