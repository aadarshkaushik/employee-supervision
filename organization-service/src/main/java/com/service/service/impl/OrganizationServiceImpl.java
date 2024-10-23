package com.service.service.impl;

import com.service.dto.OrganizationDto;
import com.service.entity.Organization;
import com.service.repository.OrganizationRepository;
import com.service.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private ModelMapper modelMapper;
    private OrganizationRepository organizationRepository;

    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
        return mapToDto(organizationRepository.save(mapToEntity(organizationDto)));
    }

    @Override
    public OrganizationDto getOrganizationByCode(String code) {
        Organization organization = organizationRepository.findByCode(code).orElseThrow(
                ()-> new IllegalArgumentException("Organization not found")
        );

        return mapToDto(organization);
    }

    private Organization mapToEntity(OrganizationDto organizationDto){
        return modelMapper.map(organizationDto,Organization.class);
    }
    private OrganizationDto mapToDto(Organization organization){
        return modelMapper.map(organization,OrganizationDto.class);
    }
}
