package com.antonioleiva.mvpexample.app.utils;

/**
 * Created by LDW10000000 on 20/02/2017.
 */

public class Fruit {

    private String name;
    private int imageId;

    public Fruit(String name ,int imageId){
        this.name = name;
        this.imageId = imageId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
