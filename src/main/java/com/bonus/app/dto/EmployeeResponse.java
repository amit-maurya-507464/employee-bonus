package com.bonus.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private String currency;
    private List<EmployeeDTO> employees;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployeeDTO {
        private String empName;
        private int amount;
    }
}