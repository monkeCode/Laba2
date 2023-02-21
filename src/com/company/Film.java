package com.company;

import java.util.Date;

enum FilmType
{
    D2(1), D3(2), D4(4);
    private final int _maskCode;
    FilmType(int code){
        _maskCode = code;
    }

    public int get_maskCode() {
        return _maskCode;
    }
}

enum FilmGenre {Horror, Triller, Drama, Comedy}

record Film(String name, Date createDate, int length, FilmGenre genre, FilmType type) {

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
