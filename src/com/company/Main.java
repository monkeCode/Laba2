package com.company;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static Client Login(String name)
    {
        var c = WorkNet.getInstance().clients.stream().filter(client -> client.get_name().equals(name)).findFirst();
        if(c.isEmpty())
            throw new IllegalArgumentException("client doesn't exist");
        return c.get();
    }
    private static Client Register(String name, String phone, String email)
    {
        var client = new Client(name,phone,email);
        WorkNet.getInstance().clients.add(client);
        return client;
    }

    private static void ClientWorkflow(Client client) throws ParseException {
        while (true) {

        System.out.println("1.get film by name\n2.get film by date\n3.get film by price");
        Scanner scanner = new Scanner(System.in);
        Cinema c = WorkNet.getInstance().cinemas.get(0);
        List<Session> sessions;
        switch (scanner.nextInt()) {
            case 1 -> sessions = c.GetSessionsByName(scanner.next());
            case 2 -> sessions = c.GetSessionsByDate(new SimpleDateFormat("dd.MM.yyyy").parse(scanner.next()));
            case 3 -> sessions = c.GetSessionsByPrice(client.get_money());
            default -> {
                continue;
            }
        }
        for(int i = 0; i < (sessions != null ? sessions.size() : 0); i++)
        {
            System.out.println(i+" " + sessions.get(i));
        }
        if(sessions.size() == 0)
        {
            System.out.println("Films not founded");
            continue;
        }
        var session = sessions.get(scanner.nextInt());
        var scheme = c.GetSchemeToSession(session);
       for(int i = 0; i < scheme.length; i++)
       {
           for (int j = 0; j < scheme[i].length; j++)
           {
               if(!scheme[i][j])
                   System.out.print(i+""+j);
               System.out.print("\t");
           }
           System.out.println();
       }
       var total = c.GetTotalPrice(session, client);
       System.out.print("Price: "+ session.get_price());
       if(session.get_price() != total)
           System.out.println(", but for you: "  + total);
       else System.out.println();

       int x,y;
       y = scanner.nextInt();
       x = scanner.nextInt();
       if(scheme[y][x])
       {
           System.out.println("this seat is taken");
           continue;
       }
       try
       {
           PaymentSystem.BuyTicket(client,c,session,y,x);
       }
       catch (IllegalArgumentException e)
       {
           System.out.println(e.getMessage());
       }

       System.out.println("type zero if you wanna exit");
       if(scanner.next().equals("0")) return;
    }

    }
    private static void AdminWorkflow(Administrator administrator)
    {
        var perms = administrator.GetPermissions().stream().toList();
        var scanner = new Scanner(System.in);

        System.out.println("Select cinema");

        for (int i = 0; i < WorkNet.getInstance().cinemas.size(); i++)
        {
            System.out.println(i + " "+ WorkNet.getInstance().cinemas.get(i));
        }
        var cinema = WorkNet.getInstance().cinemas.get(scanner.nextInt());

        System.out.println("Select action");

        for (int i = 0; i < perms.size(); i++)
        {
            System.out.println(i + " "+ perms.get(i));
        }

        perms.get(scanner.nextInt()).Operation(cinema);

    }

    public static void main(String[] args) throws ParseException {

        Administrator basedAdmin = new Administrator("Admin", "000", "zero@zero.com", "123");
        basedAdmin
                .AddPermission(new GetSoldTickedCount())
                .AddPermission(new GetTotalIncomes())
                .AddPermission(new GetStatistic())
                .AddPermission(new GetClientStatus());
        Administrator redactor = new Administrator("Redactor", "000", "zero@zero.com", "333");
        redactor.AddPermission(new ChangeCinema());
        WorkNet.getInstance().clients.add(basedAdmin);
        WorkNet.getInstance().clients.add(redactor);

        while (true)
        {
            System.out.println("1.login as user\n2.register as user");
            var scaner = new Scanner(System.in);
            Client client;

            switch (scaner.nextInt())
            {
                case 1:
                    try {
                        client = Login(scaner.next());
                        if(client.get_status() == Client.ClientStatus.Admin)
                        {
                            System.out.println("input pass");
                            if(!((Administrator)client).Validate(scaner.next()))
                            {
                                System.out.println("incorrect pass");
                                continue;
                            }
                        }

                    } catch (IllegalArgumentException ex)
                    {
                        System.out.println(ex.getMessage());
                        client = Register(scaner.next(),scaner.next(),scaner.next());
                    }
                    break;
                case 2:
                    client = Register(scaner.next(),scaner.next(),scaner.next());
                    break;
                default: continue;

            }
            if(client.get_status() == Client.ClientStatus.Admin)
            {
                System.out.println("1.login to admin workflow\n2. login to client workflow");
                if(scaner.nextInt() == 1)
                {
                    AdminWorkflow((Administrator) client);

                }
                else ClientWorkflow(client);
            }
            else
                ClientWorkflow(client);
            System.out.println("type zero to exit");
            if(scaner.next().equals("0"))
                break;
        }
        try {
            WorkNet.getInstance().Save();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void InitHardcode() {

        var film = new Film("Robots", new Date(),90, FilmGenre.Triller, FilmType.D3);
        var film2 = new Film("Comedy", new Date(),90, FilmGenre.Comedy, FilmType.D2);
        var defCin = Builder.CreateNewCinema("Bad Movies", "default cinema")
                .AddHalls(new Hall(HallType.Simple, 100, FilmType.D2),
                          new Hall(HallType.Comfort, 49, FilmType.D2,FilmType.D3),
                          new Hall(HallType.Imax,36,FilmType.D2,FilmType.D3,FilmType.D4))
                .AddSessions(new Session(new Date(), film, HallType.Imax, 700),
                             new Session(new Date(), film, HallType.Comfort, 500),
                             new Session(new Date(),film2, HallType.Simple, 300))
                .Build();
        WorkNet.getInstance().cinemas.add(defCin);
    }
}
