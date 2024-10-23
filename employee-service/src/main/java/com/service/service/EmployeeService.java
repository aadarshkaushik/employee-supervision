package com.service.service;

import com.service.payload.APIResponseDto;
import com.service.payload.EmployeeDto;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    APIResponseDto getEmployeeById(Long employeeId);
}