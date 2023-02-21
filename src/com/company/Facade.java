package com.company;

import java.util.Collection;
import java.util.Scanner;

interface Facade {
    public Collection<Subsystem> GetPermissions();

    public void AddPermission(Subsystem subsystem);
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
        System.out.println("Total incomes: "+ cinema.GetIncomes());
    }
    @Override
    public String toString() {
        return "Get total incomes";
    }
}