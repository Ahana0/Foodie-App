package com.example.foodie.chefFoodPanel;

import java.io.Serializable;

public class FoodDetails implements Serializable {
    private String Dishes,Quantity,Price,Description,ImageURL,RandomUID,chefId,chefName;

    public FoodDetails(String dishes, String quantity, String price, String description, String imageURL, String randomUID, String chefId,String chefName) {
        this.Dishes = dishes;
        this.Quantity = quantity;
        this.Price = price;
        this.Description = description;
        this.ImageURL = imageURL;
        this.RandomUID = randomUID;
        this.chefId = chefId;
        this.chefName = chefName;
    }

    public FoodDetails() {
    }

    public String getDishes() {
        return Dishes;
    }

    public void setDishes(String dishes) {
        Dishes = dishes;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public String getChefId() {
        return chefId;
    }

    public void setChefId(String chefId) {
        this.chefId = chefId;
    }

    public String getChefName() {
        return chefName;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }
}
