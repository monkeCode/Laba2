package com.company;

import java.util.*;

class Cinema {

    private String _name;
    private String _information;
    private List<Hall> _halls = new ArrayList<>();

    static class AllocatedSession
    {
        public Session session;
        public boolean[][] seats;
        public Hall hall;
        public int price;

        public AllocatedSession(Session session, Hall hall)
        {
            this.session = session;
            this.hall = hall;
            var sqrtCapacity = Math.sqrt(hall.capacity());
            seats = new boolean[(int)Math.floor(sqrtCapacity)][(int)Math.ceil(sqrtCapacity)];
        }
    }

    private final List<Check> _checks = new ArrayList<>();
    private final List<AllocatedSession> _sessions = new ArrayList<>();

    public Cinema(String name, String information, Collection<Hall> halls) {
        _name = name;
        _information = information;
        _halls.addAll(halls);
    }

    public String get_name() {
        return _name;
    }

    public void AddSession(Session s) {

        var hall = _halls.stream().
                filter(h -> h.type() == s.get_hallType()
                        && _sessions.stream().noneMatch(ses -> ses.hall == h && ses.session.get_date() == s.get_date())
                        && h.CanShow(s.get_film())).findFirst();
        if(hall.isEmpty())
            throw new IllegalArgumentException("hall not founded");
        var ses = new AllocatedSession(s, hall.get());
        ses.price = PriceModification(ses);
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

    protected int PriceModification(AllocatedSession session)
    {
        return session.session.get_price();
    }

    public int GetTotalPrice(Session s, Client c)
    {
        var status = c.get_status();
        var modification = 0.0f;

        switch (status)
        {
            case Vip ->  modification = 0.8f;
            case Friend -> modification = 0.9f;
            case Admin -> modification = 0.5f;
            default -> modification = 1.0f;
        }
       return (int) (modification * _sessions.stream().filter(ses -> ses.session == s).findFirst().get().price);
    }

    public void CommitCheck(Check check)
    {
       var session =  _sessions.stream().filter(s -> s.session == check.session()).findFirst().get();
       session.seats[check.i()][check.j()] = true;
        _checks.add(check);
    }

    public List<Check> GetChecks()
    {
        return new ArrayList<>(_checks);
    }

    public void Admit(Visitor visitor)
    {
        visitor.Visit(_halls, _checks, _sessions);
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
