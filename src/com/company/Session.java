package com.company;

import java.util.Date;

class Session {
    private Date _date;
    private Film _film;
    private int _price;
    private HallType _hallType;

    public Session(Date date, Film film,HallType hallType, int price) {
        _date = date;
        _film = film;
        _price = price;
        _hallType = hallType;
    }

    public Date get_date() {
        return _date;
    }

    public Film get_film() {
        return _film;
    }

    public int get_price(){return _price;}

    public HallType get_hallType() {
        return _hallType;
    }

    @Override
    public String toString() {
        return "Session in " + _date.toString() + " film: " + _film.name() + " hall type: " +_hallType.toString();
    }
}
