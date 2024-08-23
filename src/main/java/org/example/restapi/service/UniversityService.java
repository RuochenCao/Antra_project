package org.example.restapi.service;

import org.example.restapi.entity.University;

public interface UniversityService {
    public University[] getAllUniversityByCountry(String countryNames);

    public University[] getAllUniversity();
}
