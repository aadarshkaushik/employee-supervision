package com.service.service.impl;
import com.service.entity.Department;
import com.service.payload.DepartmentDto;
import com.service.repository.DepartmentRepository;
import com.service.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private ModelMapper modelMapper;
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        return mapToDto(departmentRepository.save(mapToEntity(departmentDto)));
    }

    @Override
    public DepartmentDto getDepartmentByCode(String departmentCode) {
        return mapToDto(departmentRepository.findByDepartmentCode(departmentCode));
    }
    //CONVERTING ENTITY INTO DTO
    private DepartmentDto mapToDto(Department department) {
        return modelMapper.map(department, DepartmentDto.class);
    }
    //CONVERTING DTO INTO ENTITY
    private Department mapToEntity(DepartmentDto departmentDto) {
        return modelMapper.map(departmentDto, Department.class);
    }
}