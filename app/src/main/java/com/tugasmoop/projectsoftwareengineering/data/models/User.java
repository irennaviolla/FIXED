package com.tugasmoop.projectsoftwareengineering.data.models;

import java.util.List;

public class User {
    private String name;
    private String email;
    private String username;
    private String password;
    private String address;
    private String phoneNumber;
    private String DOB;
    private Servicer servicer;
    private int favCount;
    private int transactionCount;
    private List<String>favServicer;
    private List<Transaction>transactionList;
    private int checkPhoto; //0 = no photo; 1 = already have photo

    public User(String name, String email, String username, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = "";
        this.DOB = "";
        this.servicer = new Servicer();
        this.favCount = 0;
        this.transactionCount = 0;
        this.checkPhoto = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public Servicer getServicer() {return servicer;}

    public void setServicer(Servicer servicer) {this.servicer = servicer;}

    public int getFavCount() {
        return favCount;
    }

    public void setFavCount(int favCount) {
        this.favCount = favCount;
    }

    public List<String> getFavServicer() {
        return favServicer;
    }

    public void setFavServicer(List<String> favServicer) {
        this.favServicer = favServicer;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public int getCheckPhoto() {
        return checkPhoto;
    }

    public void setCheckPhoto(int checkPhoto) {
        this.checkPhoto = checkPhoto;
    }
}
