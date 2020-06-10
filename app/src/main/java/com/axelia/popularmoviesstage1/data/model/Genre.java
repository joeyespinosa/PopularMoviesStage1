package com.axelia.popularmoviesstage1.data.model;

import com.google.gson.annotations.SerializedName;

public class Genre {

    @SerializedName("id")
    public Integer id = 0;

    @SerializedName("name")
    String name = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
