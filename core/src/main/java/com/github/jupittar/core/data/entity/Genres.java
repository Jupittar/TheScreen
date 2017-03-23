package com.github.jupittar.core.data.entity;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Genres {
  @SerializedName("id")
  private int id;
  @SerializedName("name")
  private String name;

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
}
