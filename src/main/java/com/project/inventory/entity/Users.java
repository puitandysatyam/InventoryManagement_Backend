package com.project.inventory.entity;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Users")
public class Users {

    private String name, password, role;

    public Users(){}

    Users(String name, String password, String role){
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
