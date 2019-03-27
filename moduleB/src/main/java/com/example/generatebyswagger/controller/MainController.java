package com.example.generatebyswagger.controller;

import com.example.generatebyswagger.api.GetDataApi;
import com.example.generatebyswagger.service.DataService;
import com.example.hibernate.entity.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MainController implements GetDataApi {
    private DataService service;

    @Autowired
    public MainController(DataService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<List<Data>> getData() {
        return service.getData();
    }
}
