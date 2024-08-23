package org.example.restapi.service;

import org.example.restapi.entity.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class UniversityServiceImpl implements UniversityService{
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public University[] getAllUniversityByCountry(String countryNames) {
        String baseUrl = "http://universities.hipolabs.com/search?country=";
        String[] countryArray = countryNames.split("&");

        // List of futures for each country's university fetch
        List<CompletableFuture<List<University>>> futures = new ArrayList<>();

        for (String country : countryArray) {
            String url = baseUrl + country;
            futures.add(fetchUniversitiesAsync(url));
        }

        // Wait for all futures to complete and collect the results
        List<University> universityList = futures.stream()
                .map(CompletableFuture::join) // Wait for each future to complete
                .flatMap(List::stream)        // Flatten the stream of lists into a single list
                .collect(Collectors.toList());

        return universityList.toArray(new University[0]);
    }
    public CompletableFuture<List<University>> fetchUniversitiesAsync(String url) {
        return CompletableFuture.supplyAsync(() -> {
            University[] universitiesArray = restTemplate.getForObject(url, University[].class);
            return Arrays.asList(universitiesArray);
        });
    }

    @Override
    public University[] getAllUniversity() {
        String url = "http://universities.hipolabs.com/search?";
        University[] universities = restTemplate.getForObject(url, University[].class);
        return universities;
    }
}
