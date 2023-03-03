package Tests;
import com.company.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.AccessDeniedException;
import java.util.Date;

class CinemaTests {


    private Cinema cinema;
    private Session session;
    private Client client;


    @BeforeEach
    void setup()
    {
        session = new Session(new Date(), new Film("n",new Date(),90, FilmGenre.Comedy,FilmType.D2), HallType.Simple, 200);
        cinema =  Builder.CreateNewCinema("C","c")
                .AddHalls(new Hall(HallType.Simple, 4, FilmType.D2))
                .AddSessions(session).Build();
        client = new Client("n","ph", "mail");
    }

    @Test
    @DisplayName("Buy ticket")
    void buyingTicketTest() throws AccessDeniedException {
        var clientMoney = client.get_money();
        PaymentSystem.BuyTicket(client,cinema,session,0,0);
        Assertions.assertEquals(client.get_total_tickets(),1);
        Assertions.assertEquals(client.get_money(),clientMoney-client.get_total_spend());
    }
    @Test
    @DisplayName("buying one ticket 2 times ")
    void buyingManyTicketsTest()
    {
       Assertions.assertThrows(AccessDeniedException.class, () ->{
           for(int i = 0; i < 2; i++)
           {
               PaymentSystem.BuyTicket(client,cinema,session,0,0);
           }
       } );


    }
    @Test
    @DisplayName("out of bounds cinema ")
    void indexOutOfRangeTest()
    {
       Assertions.assertThrows(IndexOutOfBoundsException.class, () ->{
               PaymentSystem.BuyTicket(client,cinema,session,3,0);
       });
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->{
            PaymentSystem.BuyTicket(client,cinema,session,0,3);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->{
            PaymentSystem.BuyTicket(client,cinema,session,-5,0);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class, () ->{
            PaymentSystem.BuyTicket(client,cinema,session,0,-100);
        });
    }
}