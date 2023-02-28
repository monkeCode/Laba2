package com.company;

import java.util.List;

public interface Visitor
{
    public void Visit(List<Hall> halls, List<Check> _checks, List<Cinema.AllocatedSession> _sessions);
}
