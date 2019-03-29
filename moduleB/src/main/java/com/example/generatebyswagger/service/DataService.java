package com.example.generatebyswagger.service;

//import com.example.generatebyswagger.api.GetDataApiDelegate;
import com.example.hibernate.repository.DataRepository;
import com.example.hibernate.entity.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService  {
    private DataRepository dataRepository = DataRepository.getInstance();

    public ResponseEntity<List<Data>> getData() {
        List<Data> dataList = dataRepository.getData();
        return new ResponseEntity<>(dataList, HttpStatus.OK);
    }
}
