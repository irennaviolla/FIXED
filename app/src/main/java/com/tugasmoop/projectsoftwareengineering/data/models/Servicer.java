package com.tugasmoop.projectsoftwareengineering.data.models;

import com.tugasmoop.projectsoftwareengineering.data.models.Transaction;

import java.util.List;

public class Servicer {
    private int status;
    private String category;
    private int wallet;
    private String NIK;
    private String name;
    private String noRek;
    private String bank;
    private String description;
    private int rating;
    private int custCount;//berapa orang yang rating
    private int price;
    private int transactionCount;
    private List<Transaction>transactionList;

    public Servicer() {
        this.status = 0;//0 is unverified, 1 is verified, 8 is pending
        this.category = "";
        this.wallet = 0;
        this.NIK = "";
        this.name = "";
        this.noRek = "";
        this.bank = "";
        this.description = "";
        this.rating = 0;
        this.custCount = 0;
        this.price = 0;
        this.transactionCount = 0;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public String getNIK() {
        return NIK;
    }

    public void setNIK(String NIK) {
        this.NIK = NIK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoRek() {
        return noRek;
    }

    public void setNoRek(String noRek) {
        this.noRek = noRek;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getCustCount() {
        return custCount;
    }

    public void setCustCount(int custCount) {
        this.custCount = custCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
}
