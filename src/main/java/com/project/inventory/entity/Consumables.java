package com.project.inventory.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Consumables")
public class Consumables {

    private String name, quantity, consumed, warningLevel, date;
    public Consumables(String name, String quantity, String consumed, String warningLevel, String date) {
        this.name = name;
    }

    public Consumables() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public String getConsumed() {
        return consumed;
    }
    public void setConsumed(String consumed) {
        this.consumed = consumed;
    }
    public String getWarningLevel() {
        return warningLevel;
    }
    public void setWarningLevel(String warningLevel) {
        this.warningLevel = warningLevel;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

}
