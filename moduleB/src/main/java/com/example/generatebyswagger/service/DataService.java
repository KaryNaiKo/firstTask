package com.example.generatebyswagger.service;

import com.example.generatebyswagger.api.GetDataApiDelegate;
import com.example.hibernate.dao.DataDAO;
import com.example.hibernate.entity.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService implements GetDataApiDelegate {
    private DataDAO dataDAO = DataDAO.getInstance();

    @Override
    public ResponseEntity<List<Data>> getData() {
        List<Data> dataList = dataDAO.getData();
        return new ResponseEntity<>(dataList, HttpStatus.OK);
    }
}
