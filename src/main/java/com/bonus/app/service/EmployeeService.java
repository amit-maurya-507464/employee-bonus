package com.bonus.app.service;

import com.bonus.app.dto.EmployeeResponse;
import com.bonus.app.entity.Department;
import com.bonus.app.entity.Employee;
import com.bonus.app.repository.DepartmentRepository;
import com.bonus.app.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public void saveEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            Department department = departmentRepository.findByName(employee.getDepartment().getName())
                    .orElseGet(() -> departmentRepository.save(new Department(null, employee.getDepartment().getName())));
            employee.setDepartment(department);
            employeeRepository.save(employee);
        }
    }

    public List<EmployeeResponse> getEligibleEmployees(LocalDate date) {
        List<Employee> employees = employeeRepository.findByExitDateAfter(date);
        Map<String, List<Employee>> groupedByCurrency = employees.stream()
                .collect(Collectors.groupingBy(Employee::getCurrency));

        List<EmployeeResponse> responses = new ArrayList<>();
        groupedByCurrency.forEach((currency, employee) -> {
            EmployeeResponse response = new EmployeeResponse();
            response.setCurrency(currency);
            response.setEmployees(employee.stream()
                    .map(emp -> new EmployeeResponse.EmployeeDTO(emp.getEmpName(), emp.getAmount()))
                    .sorted(Comparator.comparing(EmployeeResponse.EmployeeDTO::getEmpName))
                    .collect(Collectors.toList()));
            responses.add(response);
        });
        return responses;
    }
}
