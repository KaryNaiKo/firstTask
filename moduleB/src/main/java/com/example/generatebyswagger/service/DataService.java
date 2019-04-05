package com.example.generatebyswagger.service;

import com.example.hibernate.repository.DataRepository;
import com.example.hibernate.entity.Data;
import com.example.jedis.JedisUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DataService {
    private DataRepository dataRepository = DataRepository.getInstance();
    private JedisUtil jedis = JedisUtil.getJedisUtil();

    public ResponseEntity<List<Data>> getData() {
        dataRepository.clear();
        List<Data> dataList = dataRepository.getDataWithCriteria("");
        return new ResponseEntity<>(dataList, HttpStatus.OK);
    }

    public ResponseEntity<Set<String>> getDataFromJedis() {
        Set<String> dataSet = jedis.getAll();
        return new ResponseEntity<>(dataSet, HttpStatus.OK);
    }
}
