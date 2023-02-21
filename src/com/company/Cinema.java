package com.company;

import java.util.*;

class Cinema {

    private String _name;
    private int _formatMask;
    private String _information;
    private int _capacity;
    private class AllocatedSession
    {
        public Session session;
        public boolean[][] seats;

        public int price;

        public AllocatedSession(Session session, int capacity)
        {
            this.session = session;
            var sqrtCapacity = Math.sqrt(capacity);
            seats = new boolean[(int)Math.floor(sqrtCapacity)][(int)Math.ceil(sqrtCapacity)];
        }
    }

    private final List<Check> _checks = new ArrayList<>();

    private final List<AllocatedSession> _sessions;

    public Cinema(String name, String information, FilmType[] supportedTypes, int capacity) {
        _name = name;
        _information = information;
        _formatMask = 0;
        _capacity = capacity;

        for (var type : supportedTypes) {
            _formatMask ^= type.get_maskCode();
        }
        _sessions = new ArrayList<>();
    }

    public String get_name() {
        return _name;
    }

    public void AddSession(Session s) {

        if (_sessions.stream().anyMatch(session -> session.session.get_date() == s.get_date()))
            throw new IllegalArgumentException("this time is already taken");
        if((s.get_film().type().get_maskCode()& _formatMask) == 0)
            throw new IllegalArgumentException("cinema not supported this film");
        var ses = new AllocatedSession(s, _capacity);
        ses.price = PriceModification(s);
        _sessions.add(ses);
    }

    public List<Session> GetSessionsByName(String filmName) {
        return _sessions.stream().
                filter(session -> session.session.get_film().name().equals(filmName)).
                map( s -> s.session).toList();
    }

    public List<Session> GetSessionsByDate(Date filmDate) {
        return _sessions.stream().filter(session -> session.session.get_date() == filmDate).map(s -> s.session).toList();
    }

    public List<Session> GetSessionsByPrice(int maxPrice) {

        return _sessions.stream().
                filter(session -> session.price <= maxPrice).map( s -> s.session).toList();
    }

    protected int PriceModification(Session session) {
        return session.get_price();
    }

    public int GetTotalPrice(Session s)
    {
       return  _sessions.stream().filter(ses -> ses.session == s).findFirst().get().price;
    }

    public void CommitCheck(Check check)
    {
       var session =  _sessions.stream().filter(s -> s.session == check.session()).findFirst().get();
       session.seats[check.i()][check.j()] = true;
        _checks.add(check);
    }

    public int GetIncomes() {
        return _checks.stream().mapToInt(Check::price).sum();
    }

    public boolean[][] GetSchemeToSession(Session s)
    {
        return  _sessions.stream().filter(ses -> ses.session == s).findFirst().get().seats.clone() ;
    }

    @Override
    public String toString() {
        return _name + " cinema";
    }
}
