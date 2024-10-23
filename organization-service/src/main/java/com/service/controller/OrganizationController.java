package com.service.controller;

import com.service.dto.OrganizationDto;
import com.service.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/organizations")
public class OrganizationController {

    private OrganizationService organizationService;

    //http://localhost:8083/api/organizations/
    @PostMapping("/")
    public ResponseEntity<OrganizationDto> saveOrganization(@RequestBody OrganizationDto organizationDto){
        return new ResponseEntity<>(organizationService.saveOrganization(organizationDto), HttpStatus.CREATED);
    }
    //http://localhost:8083/api/organizations/{code}
    @GetMapping("/{code}")
    public ResponseEntity<OrganizationDto> getOrganization(@PathVariable String code){
        return ResponseEntity.ok(organizationService.getOrganizationByCode(code));
    }
}
