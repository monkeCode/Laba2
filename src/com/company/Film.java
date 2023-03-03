package com.company;

import java.io.Serializable;
import java.util.Date;

public record Film(String name, Date createDate, int length, FilmGenre genre, FilmType type) implements Serializable {

    @Override
    public String toString() {
        return "Film{" +
                "name='" + name + '\'' +
                ", createDate=" + createDate +
                ", length=" + length +
                ", genre=" + genre +
                ", type=" + type +
                '}';
    }
}
