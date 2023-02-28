package com.company;

import java.util.ArrayList;
import java.util.List;

public class Builder
{
    public static class CinemaBuilder
    {
        private final String _name;
        private final String _information;
        private final List<Hall> _halls = new ArrayList<>();
        private final List<Session> _sessions = new ArrayList<>();
        protected CinemaBuilder(String name, String inf)
        {
            _name = name;
            _information = inf;
        }

        public CinemaBuilder AddHalls(Hall... halls)
        {
            _halls.addAll(List.of(halls));
            return this;
        }
        public CinemaBuilder AddSessions(Session... sessions)
        {
            _sessions.addAll(List.of(sessions));
            return this;
        }
        public Cinema Build()
        {
            var cinema = new Cinema(_name, _information, _halls);
            for(var session:_sessions)
            {
                cinema.AddSession(session);
            }
            return cinema;
        }

    }

    public static CinemaBuilder CreateNewCinema(String name, String information)
    {
        return new CinemaBuilder(name,information);
    }
}
