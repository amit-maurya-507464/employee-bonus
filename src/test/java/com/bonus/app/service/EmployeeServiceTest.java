
package com.bonus.app.service;

import com.bonus.app.dto.EmployeeResponse;
import com.bonus.app.entity.Department;
import com.bonus.app.entity.Employee;
import com.bonus.app.repository.DepartmentRepository;
import com.bonus.app.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveEmployees() {
        List<Employee> employees = List.of(
            new Employee(null, "raj singh", new Department(null, "accounts"), 5000, "INR",
                    LocalDate.of(2022, 5, 20), LocalDate.of(2023, 5, 20)),
            new Employee(null, "pratap m", new Department(null, "accounts"), 3000, "INR",
                    LocalDate.of(2021, 1, 1), LocalDate.of(2023, 5, 20))
        );

        when(departmentRepository.findByName("accounts")).thenReturn(Optional.of(new Department(1L, "accounts")));

        employeeService.saveEmployees(employees);

        verify(employeeRepository, times(2)).save(any(Employee.class));
    }

    @Test
    public void testGetEligibleEmployees() {
        LocalDate givenDate = LocalDate.of(2022, 5, 27);

        List<Employee> employees = List.of(
            new Employee(1L, "raj singh", new Department(1L, "accounts"), 5000, "INR",
                    LocalDate.of(2022, 5, 20), LocalDate.of(2023, 5, 20)),
            new Employee(2L, "pratap m", new Department(1L, "accounts"), 3000, "INR",
                    LocalDate.of(2021, 1, 1), LocalDate.of(2023, 5, 20)),
            new Employee(3L, "sushmita lal", new Department(2L, "IT"), 4000, "INR",
                    LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31)),
            new Employee(4L, "sam", new Department(3L, "Operations"), 2500, "USD",
                    LocalDate.of(2022, 5, 20), LocalDate.of(2023, 5, 20)),
            new Employee(5L, "john", new Department(3L, "Operations"), 2500, "USD",
                    LocalDate.of(2023, 1, 20), LocalDate.of(2024, 12, 30)),
            new Employee(6L, "susan", new Department(2L, "IT"), 700, "USD",
                    LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 31))
        );

        when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeResponse> eligibleEmployees = employeeService.getEligibleEmployees(givenDate);

        assertThat(eligibleEmployees).hasSize(2);
        assertThat(eligibleEmployees.get(0).getCurrency()).isEqualTo("USD");
        assertThat(eligibleEmployees.get(0).getEmployees()).hasSize(2);
        assertThat(eligibleEmployees.get(1).getCurrency()).isEqualTo("INR");
        assertThat(eligibleEmployees.get(1).getEmployees()).hasSize(2);
    }
}
