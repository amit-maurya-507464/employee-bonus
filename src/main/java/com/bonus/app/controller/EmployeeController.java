package com.bonus.app.controller;

import com.bonus.app.dto.EmployeeRequest;
import com.bonus.app.dto.EmployeeResponse;
import com.bonus.app.service.EmployeeService;
import com.bonus.app.utils.MyUtils;
import com.bonus.app.entity.Department;
import com.bonus.app.entity.Employee;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tci")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee-bonus")
    public ResponseEntity<Object> saveEmployees(@Valid @RequestBody EmployeeRequest request) {
        List<Employee> employees = request.getEmployees().stream().map(dto -> {
            Department department = new Department();
            department.setName(dto.getDepartment());
            return new Employee(null, dto.getEmpName(), department, dto.getAmount(), dto.getCurrency(),
                                LocalDate.parse(dto.getJoiningDate(), DateTimeFormatter.ofPattern("MMM-dd-yyyy")),
                                LocalDate.parse(dto.getExitDate(), DateTimeFormatter.ofPattern("MMM-dd-yyyy")));
        }).collect(Collectors.toList());
        employeeService.saveEmployees(employees);
        return ResponseEntity.ok("Employee created Successfully");
    }

    @GetMapping("/employee-bonus")
    public ResponseEntity<Map<String, Object>> getEligibleEmployees(@RequestParam("date") @DateTimeFormat(pattern = "MMM-dd-yyyy") String dateString) {
        LocalDate requestDate = MyUtils.parseDate(dateString);
        if (requestDate == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("errorMessage", "Invalid date format, expected format is MMM-dd-yyyy");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        List<EmployeeResponse> data = employeeService.getEligibleEmployees(requestDate);
        Map<String, Object> response = new HashMap<>();
        response.put("errorMessage", "");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }
}
