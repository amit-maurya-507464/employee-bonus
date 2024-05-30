// EmployeeControllerTest.java
package com.bonus.app.controller;

import com.bonus.app.dto.EmployeeRequest;
import com.bonus.app.dto.EmployeeResponse;
import com.bonus.app.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSaveEmployees() throws Exception {
        EmployeeRequest request = new EmployeeRequest();
        request.setEmployees(List.of(
            new EmployeeRequest.EmployeeDTO("raj singh", "accounts", 5000, "INR", "May-20-2022", "May-20-2023"),
            new EmployeeRequest.EmployeeDTO("pratap m", "accounts", 3000, "INR", "Jan-01-2021", "May-20-2023")
        ));

        mockMvc.perform(post("/tci/employee-bonus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEligibleEmployees() throws Exception {
        List<EmployeeResponse> data = List.of(
                new EmployeeResponse("INR", List.of(
                        new EmployeeResponse.EmployeeDTO("raj singh", 5000),
                        new EmployeeResponse.EmployeeDTO("pratap m", 3000)
                )),
                new EmployeeResponse("USD", List.of(
                        new EmployeeResponse.EmployeeDTO("sam", 2500),
                        new EmployeeResponse.EmployeeDTO("susan", 700)
                ))
        );

        when(employeeService.getEligibleEmployees(LocalDate.of(2022, 5, 27))).thenReturn(data);

        mockMvc.perform(get("/tci/employee-bonus")
                        .param("date", "May-27-2022")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Map.of("errorMessage", "", "data", data))));
    }
}
