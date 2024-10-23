package com.service.service.impl;
import com.service.entity.Employee;
import com.service.payload.APIResponseDto;
import com.service.payload.DepartmentDto;
import com.service.payload.EmployeeDto;
import com.service.payload.OrganizationDto;
import com.service.repository.EmployeeRepository;
import com.service.service.APIClient;
import com.service.service.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private ModelMapper modelMapper;
    private EmployeeRepository employeeRepository;
    private APIClient apiClient;
    private WebClient webClient;
    //private RestTemplate restTemplate;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        return mapToDto(employeeRepository.save(mapToEntity(employeeDto)));
    }

    @CircuitBreaker(
            name = "${spring.application.name}",
            fallbackMethod = "getDefaultDepartment"
    )
   /* @Retry(
            name = "${spring.application.name}",
            fallbackMethod = "getDefaultDepartment"
    )*/
    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {
        LOGGER.info("inside getEmployeeById() method");
        Employee employee = employeeRepository.findById(employeeId).get();

        /*
        deprecated rest template method of api calling in microservice
        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8081/departments/"+employee.getDepartmentCode(), DepartmentDto.class);
        DepartmentDto departmentDto = responseEntity.getBody();
        */

        //DepartmentDto departmentDto = apiClient.getDepartment(employee.getDepartmentCode());

        //web client method of calling the rest api of another service
        DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8080/departments/"+employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();

        //web client method of calling the rest api of another service
        OrganizationDto organizationDto = webClient.get()
                .uri("http://localhost:8083/api/organizations/"+employee.getOrganizationCode())
                .retrieve()
                .bodyToMono(OrganizationDto.class)
                .block();

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(mapToDto(employee));
        apiResponseDto.setDepartment(departmentDto);
        apiResponseDto.setOrganization(organizationDto);
        return apiResponseDto;
    }
    public APIResponseDto getDefaultDepartment(Long employeeId,Exception exception){
        LOGGER.info("inside getDefaultDepartment() method");
        Employee employee = employeeRepository.findById(employeeId).get();

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("R&D Department");
        departmentDto.setDepartmentCode("RD001");
        departmentDto.setDepartmentDescription("Research and Development Department");

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(mapToDto(employee));
        apiResponseDto.setDepartment(departmentDto);
        return apiResponseDto;
    }

    //CONVERTING ENTITY INTO DTO
    private EmployeeDto mapToDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }
    //CONVERTING DTO INTO ENTITY
    private Employee mapToEntity(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
    }
}
