package com.example.admin.tourapps;

import java.io.Serializable;

/**
 * Created by Admin on 7/28/2017.
 */

public class Pojo implements Serializable {

    String name;
    String image;
    String description;

    public Pojo() {
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}


