package com.company;

import java.util.*;

interface Facade {
    public Collection<Subsystem> GetPermissions();

    public Facade AddPermission(Subsystem subsystem);
}

interface Subsystem
{
    public void Operation(Cinema cinema);
}

class GetSoldTickedCount implements Subsystem
{

    private Session SelectSession(Cinema c)
    {
       var sessions = c.GetSessionsByPrice(Integer.MAX_VALUE).stream().toList();
       for(int i = 0; i < sessions.size(); i++)
       {
           System.out.println(i+" "+ sessions.get(i));
       }
       Scanner s = new Scanner(System.in);
       var res = s.nextInt();
       return sessions.get(res);
    }
    @Override
    public void Operation(Cinema cinema) {
        int count = 0;

        for(var places:cinema.GetSchemeToSession(SelectSession(cinema)))
            for (var place: places)
            {
                if(place)
                    count++;
            }
        System.out.println("Total sold: "+count);
    }

    @Override
    public String toString() {
        return "Get sold ticked count";
    }
}

class GetTotalIncomes implements Subsystem
{

    @Override
    public void Operation(Cinema cinema) {
        System.out.println("Total incomes: "+ cinema.GetChecks().stream().mapToInt(Check::price).sum());
    }
    @Override
    public String toString() {
        return "Get total incomes";
    }
}

class GetStatistic implements Subsystem
{

    @Override
    public void Operation(Cinema cinema) {
        HashMap<HallType, Integer> stats = new HashMap<>();
       for (var check : cinema.GetChecks())
       {
           var key =check.session().get_hallType();
           if(stats.containsKey(key))
              stats.replace(key, stats.get(key) + check.price());
           else stats.put(key, check.price());
       }
       for (var i: stats.keySet())
       {
            System.out.println(i + " "+ stats.get(i));
       }
    }

    @Override
    public String toString() {
        return "Get Halls stats";
    }
}

class GetClientStatus implements Subsystem
{

    @Override
    public void Operation(Cinema cinema)
    {
        HashMap<Client.ClientStatus, Integer> stats = new HashMap<>();
        for (var client : WorkNet.getInstance().clients)
        {
            var key =client.get_status();
            if(stats.containsKey(key))
                stats.replace(key, stats.get(key) + 1);
            else stats.put(key, 1);
        }
        for (var i: stats.keySet())
        {
            System.out.println(i + " "+ stats.get(i));
        }
    }

    @Override
    public String toString() {
        return "Get client stats";
    }
}

class ChangeCinema implements Subsystem, Visitor
{

    @Override
    public void Operation(Cinema cinema)
    {
        cinema.Admit(this);
    }

    private Hall CreateHall(Scanner scanner)
    {

        System.out.println("Write capacity");
        var capacity = scanner.nextInt();
        System.out.println("Select type");
        for (int i = 0; i< HallType.values().length; i++)
        {
            System.out.println(i+" "+HallType.values()[i]);
        }
        var type = HallType.values()[scanner.nextInt()];

        Set<FilmType> filmTypes = new HashSet<>();
        do {
            System.out.println("Select supported types");
            for (int i = 0; i< FilmType.values().length; i++)
            {
                System.out.println(i+" "+FilmType.values()[i]);
            }
            filmTypes.add(FilmType.values()[scanner.nextInt()]);
            System.out.println("type zero to confirm");
        }while (!scanner.next().equals("0"));

        return new Hall(type,capacity,filmTypes.toArray(new FilmType[0]));
    }
    private void RemoveHall(List<Hall> halls, List<Cinema.AllocatedSession> sessions, Scanner scanner)
    {
        for(int i = 0; i<halls.size(); i++)
        {
            System.out.println(i+" "+ halls.get(i));
        }
        System.out.println("Select to remove");
        var removed = halls.get(scanner.nextInt());
        sessions.removeIf(se -> se.hall == removed);
        halls.remove(removed);
    }
    @Override
    public void Visit(List<Hall> halls, List<Check> checks, List<Cinema.AllocatedSession> sessions)
    {
        System.out.println("1. add hall\n2.remove hall\n");
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextInt()) {
            case 1 -> halls.add(CreateHall(scanner));
            case 2 -> RemoveHall(halls, sessions, scanner);
        }
    }

    @Override
    public String toString() {
        return "Change Halls";
    }
}