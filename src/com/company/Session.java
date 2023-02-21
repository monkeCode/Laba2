package com.company;

import java.util.Date;

class Session {
    private Date _date;
    private Film _film;
    private int _price;

    public Session(Date date, Film film, int price) {
        _date = date;
        _film = film;
        _price = price;
    }

    public Date get_date() {
        return _date;
    }

    public Film get_film() {
        return _film;
    }

    public int get_price(){return _price;}

    @Override
    public String toString() {
        return "Session in " + _date.toString() + " film: " + _film.name();
    }
}
