package com.bonus.app.dto;

import com.bonus.app.validation.ValidDate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    @NotEmpty
    @Valid
    private List<EmployeeDTO> employees;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployeeDTO {
        @NotBlank
        private String empName;

        @NotBlank
        private String department;

        @Positive
        private int amount;

        @NotBlank
        private String currency;

        @NotBlank
        @ValidDate
        private String joiningDate;

        @NotBlank
        @ValidDate
        private String exitDate;
    }
}