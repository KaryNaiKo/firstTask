package com.example.hibernate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "data")
public class Data {
    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = 100000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    @Column(name = "data1", nullable = false)
    private String data1;

    @Column(name = "data2", nullable = false)
    private String data2;

    public Data() {
    }

    public Data(int id, String data1, String data2) {
        this.id = id;
        this.data1 = data1;
        this.data2 = data2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", data1='" + data1 + '\'' +
                ", data2='" + data2 + '\'' +
                '}';
    }

    @JsonIgnore
    public boolean isPersisted() {
        return id != null;
    }
}
