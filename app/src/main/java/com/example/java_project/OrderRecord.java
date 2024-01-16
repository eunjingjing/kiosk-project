package com.example.java_project;

public class OrderRecord {
    private String username;
    private String menu;
    private String bread;
    private String sauce;
    private int price;
    private String date;

    public OrderRecord(String username, String menu, String bread, String sauce, int price, String date) {
        this.username = username;
        this.menu = menu;
        this.bread = bread;
        this.sauce = sauce;
        this.price = price;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public String getMenu() {
        return menu;
    }

    public String getBread() {
        return bread;
    }

    public String getSauce() {
        return sauce;
    }

    public int getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}

