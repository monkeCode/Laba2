package com.company;

import java.util.ArrayList;
import java.util.List;

public class WorkNet
{

    private static WorkNet _instance;

    public List<Client> clients = new ArrayList<>();

    public List<Cinema> cinemas = new ArrayList<>();

    private WorkNet(){}

    public static WorkNet getInstance()
    {
        if(_instance == null)
            _instance = new WorkNet();
        return _instance;
    }
}
