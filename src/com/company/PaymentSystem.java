package com.company;

import java.io.Serializable;

record Check(String clientName,String cinemaName, Session session, int price, int i, int j) implements Serializable
{
    @Override
    public String toString() {
        return "Client: " +clientName+"\nCinema: "+cinemaName + "\nSession: "+session+"\nTotal price: "+price;
    }
}

public final class PaymentSystem
{
    public static void BuyTicket(Client client, Cinema cinema, Session session, int i, int j)
    {
        var price = cinema.GetTotalPrice(session, client);
        Check check = new Check(client.get_name(), cinema.get_name(), session, price, i, j);
        client.CommitCheck(check);
        cinema.CommitCheck(check);
        System.out.println("Grats, your ticket:\n"+ check);
    }
}
