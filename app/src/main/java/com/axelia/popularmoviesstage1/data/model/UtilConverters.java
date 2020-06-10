package com.axelia.popularmoviesstage1.data.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class UtilConverters {

    private static Gson gson = new Gson();

    @TypeConverter
    public static String fromGenresList(List<Genre> genres) {
        return gson.toJson(genres);
    }

    @TypeConverter
    public static List<Genre> toGenresList(String genres) {
        if (genres == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Genre>>() {}.getType();

        return gson.fromJson(genres, listType);
    }
}
