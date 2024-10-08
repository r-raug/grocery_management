package com.rr.midterm_grocery_manager;

public class GroceryItem {
    private int id; // Add this field for the database ID
    private String name;
    private String category;

    public GroceryItem() {}

    public GroceryItem(String name, String category) {
        this.name = name;
        this.category = category;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

