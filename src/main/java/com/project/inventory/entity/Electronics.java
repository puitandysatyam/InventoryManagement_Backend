package com.project.inventory.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "Electronics")
public class Electronics {

    @Id
    private String uid;
    private String name, spec, commodity, quantity, assignTo, dealer, amount, date;

    Electronics() {

    }

    Electronics(String uid, String name, String spec, String commodity, String quantity, String assignTo, String dealer, String amount, String date){

        this.uid = uid;
        this.name = name;
        this.spec = spec;
        this.commodity = commodity;
        this.quantity = quantity;
        this.assignTo = assignTo;
        this.dealer = dealer;
        this.amount = amount;
        this.date = date;
    }

    //setters


    public void setName(String name) {
        this.name = name;
    }
    public void setUid(String uid){
        this.uid = uid;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    //getters


    public String getName() {
        return name;
    }

    public String getSpec() {
        return spec;
    }

    public String getCommodity() {
        return commodity;
    }

    public String getUid() {
        return uid;
    }

    public String getAmount() {
        return amount;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public String getDate() {
        return date;
    }

    public String getDealer() {
        return dealer;
    }

    public String getQuantity() {
        return quantity;
    }

}
