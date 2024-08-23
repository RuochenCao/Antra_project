package org.example.restapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.restapi.entity.University;
import org.example.restapi.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/university")
public class UniversityController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UniversityService universityService;

    @GetMapping("/{countryNames}")
    public University[] getAllUniversitiesByCountryName(@PathVariable("countryNames") String countryNames) throws JsonProcessingException {
        return universityService.getAllUniversityByCountry(countryNames);
    }

    @GetMapping
    public University[] getAllUniversities() throws JsonProcessingException {
        return universityService.getAllUniversity();
    }

    @GetMapping("/test")
    public String testMapping() {
        return "Controller is working!";
    }
}
