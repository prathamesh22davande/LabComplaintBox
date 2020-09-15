package com.example.userlogin;

public class Queries {

    String lab,computer,device,description,status;

    public Queries()
    {

    }

    public Queries(String lab, String computer, String device, String description, String status) {
        this.lab = lab;
        this.computer = computer;
        this.device = device;
        this.description = description;
        this.status = status;
    }

    public String getLab() {
        return lab;
    }

    public String getComputer() {
        return computer;
    }

    public String getDevice() {
        return device;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}
