package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class Client implements Serializable {

    public enum ClientStatus {Default, Friend, Vip, Admin}
    private String _name;
    private String _number;
    private String _email;
    private int _money;
    private List<Check> _checks = new ArrayList<>();
    public Client(String name, String number, String email) {
        _name = name;
        _number = number;
        _email = email;
        _money = 1000;
    }

    public void CommitCheck(Check check)
    {
        withdraw(check.price());
        _checks.add(check);
    }

    private void withdraw(int cost) {
        if (_money < cost)
            throw new IllegalArgumentException("money is less than cost");
        _money -= cost;
    }

    public String get_name() {
        return _name;
    }

    public String get_number() {
        return _number;
    }

    public String get_email() {
        return _email;
    }

    public int get_money() {
        return _money;
    }

    public int get_total_spend()
    {
        return _checks.stream().mapToInt(Check::price).sum();
    }
    public int get_total_tickets()
    {
        return _checks.size();
    }

    public ClientStatus get_status()
    {
        if(_checks.size() >=4)
            return ClientStatus.Vip;
        if(_checks.size() >=3)
            return ClientStatus.Friend;
        return ClientStatus.Default;
    }
}

class Administrator extends Client implements Facade
{
    private String _pass;

    private List<Subsystem> _permissions = new ArrayList<>();

    public Administrator(String name, String number, String email, String pass) {
        super(name, number, email);
        _pass = pass;
    }

    public boolean Validate(String pass)
    {
        return pass.equals(_pass);
    }

    @Override
    public Collection<Subsystem> GetPermissions() {
        return _permissions;
    }

    @Override
    public Facade AddPermission(Subsystem subsystem) {
        _permissions.add(subsystem);
        return this;
    }

    @Override
    public ClientStatus get_status() {
        return ClientStatus.Admin;
    }
}
