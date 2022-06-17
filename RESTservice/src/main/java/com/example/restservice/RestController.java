package com.example.restservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/categories")
public class RestController {

    private final List<String>  categories = Arrays.asList("handball","Sport","football","news","lifestyle","books");

    @GetMapping(produces = {"application/json","application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public List<String> getCategoriesList(){
        System.out.println(categories);
        return categories;
    }

}
