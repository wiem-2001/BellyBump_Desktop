package tn.esprit;

import tn.esprit.controllers.event.ShowEvents;
import tn.esprit.entities.Coach;
import tn.esprit.entities.Event;
import tn.esprit.entities.User;
import tn.esprit.services.CoachService;
import tn.esprit.services.EventParticipationService;
import tn.esprit.services.EventService;
import tn.esprit.util.MaConnexion;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        CoachService cs=new CoachService();
        Coach coach=new Coach(8,12345678,"eya","lamouri","psy","eya.lamouri@esprit.tn");
        //cs.add(coach);
        //cs.update(coach);
        //cs.delete(coach);
        /*for (Coach c: cs.getAll() ) {
            System.out.println(c);
        }
        System.out.println(cs.getOne(5));*/

        EventService es= new EventService();
        /*Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("13:00:00");
        Event event= new Event(116,"baby updated","url_image", " cest lévenemnt du bebe","H235dfs",
                Date.valueOf("2024-04-29"),
                startTime,
                endTime,
                false, null );
        es.add(event);
        //es.update(event);
        //es.delete(event);
        for (Event c: es.getAll() ) {
            System.out.println(c);
        }*/
        //System.out.println(es.getOne(110));
        /*EventParticipationService eps = new EventParticipationService();
        User user1= new User("admin@gmail.com", "Moulou5eya?", "Eya", "LAMOURI", null, "bizerte", "8574175fdc7c3294a8352b1fe8321fdf.jpg", true, true,  Date.valueOf("2002-01-12"), 67, 53124102);

        System.out.println("-----------------------------------------");

        Event event1 = es.getOne(113);
        //eps.add(event1,user1);
        eps.delete(event1,user1);*/


        ShowEvents controller = new ShowEvents();
        controller.getRecommendedEvents();

    }
}