package com.company;

import java.util.*;






public class Main {

    private static final List<Client> clients = new ArrayList<>();
    private static final List<Cinema> cinemas = new ArrayList<>();
    private static Client Login(String name)
    {
        var c = clients.stream().filter(client -> client.get_name().equals(name)).findFirst();
        if(c.isEmpty())
            throw new IllegalArgumentException("client doesn't exist");
        return c.get();
    }
    private static Client Register(String name, String phone, String email)
    {
        var client = new Client(name,phone,email);
        clients.add(client);
        return client;
    }

    private static void ClientWorkflow(Client client)
    {
        while (true) {

        System.out.println("1.get film by name\n2.get film by date\n3.get film by price");
        Scanner scanner = new Scanner(System.in);
        Cinema c = cinemas.get(0);
        List<Session> sessions = null;
        switch (scanner.nextInt()) {
            case 1 -> sessions = c.GetSessionsByName(scanner.next());
            case 2 -> sessions = c.GetSessionsByDate(new Date(scanner.next()));
            case 3 -> sessions = c.GetSessionsByPrice(client.get_money());
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
       System.out.println("Price: " + c.GetTotalPrice(session));
       int x,y;
       y = scanner.nextInt();
       x = scanner.nextInt();
       PaymentSystem.BuyTicket(client,c,session,y,x);
       System.out.println("type zero if you wanna exit");
       if(scanner.next().equals("0")) return;
    }

    }
    private static void AdminWorkflow(Administrator administrator)
    {
        var perms = administrator.GetPermissions().stream().toList();
        var scanner = new Scanner(System.in);

        System.out.println("Select cinema");

        for (int i = 0; i < Main.cinemas.size(); i++)
        {
            System.out.println(i + " "+ Main.cinemas.get(i));
        }
        var cinema = Main.cinemas.get(scanner.nextInt());

        System.out.println("Select action");

        for (int i = 0; i < perms.size(); i++)
        {
            System.out.println(i + " "+ perms.get(i));
        }

        perms.get(scanner.nextInt()).Operation(cinema);

    }

    public static void main(String[] args)
    {
        Administrator basedAdmin = new Administrator("Admin", "000", "zero@zero.com", "123");
        basedAdmin.AddPermission(new GetSoldTickedCount());
        basedAdmin.AddPermission(new GetTotalIncomes());

        var defCin = new Cinema("Bad movies","default",new FilmType[]{FilmType.D2, FilmType.D3}, 100);
        cinemas.add(defCin);
        defCin.AddSession(new Session(new Date(System.currentTimeMillis()),new Film("Cringe Comedy",
                new Date(System.currentTimeMillis()-10000), 90, FilmGenre.Comedy,FilmType.D2),300));
        while (true)
        {
            System.out.println("1.login as user\n2.register as user\n3.login as admin");
            var scaner = new Scanner(System.in);
            Client client = null;

            switch (scaner.nextInt())
            {
                case 1:
                    try {
                        client = Login(scaner.next());
                    } catch (IllegalArgumentException ex)
                    {
                        System.out.println(ex.getMessage());
                        client = Register(scaner.next(),scaner.next(),scaner.next());
                    }
                    break;
                case 2:
                    client = Register(scaner.next(),scaner.next(),scaner.next());
                    break;
                case 3:
                    System.out.println("input pass");
                    if(basedAdmin.Validate(scaner.next()))
                    {
                        System.out.println("1.login to admin workflow\n2. login to client workflow");
                        if(scaner.nextInt() == 1)
                        {
                            AdminWorkflow(basedAdmin);
                            continue;
                        }
                        client = basedAdmin;
                        break;
                    }
                    System.out.println("incorrect pass");
                    continue;
            }
            ClientWorkflow(client);
        }

    }
}
