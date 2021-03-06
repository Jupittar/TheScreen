package com.github.jupittar.thescreen.data.remote.response;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Company {

    //region Fields
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    //endregion

    //region Getters and Setters
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
    //endregion
}
