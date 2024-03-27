package tn.esprit;

import tn.esprit.entities.Coach;
import tn.esprit.entities.Event;
import tn.esprit.services.CoachService;
import tn.esprit.services.EventService;
import tn.esprit.util.MaConnexion;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        CoachService cs=new CoachService();
        Coach coach=new Coach(7,53124102,"eya","lamouri","psy","eya.lamouri@esprit.tn");
        //cs.add(coach);
        //cs.update(coach);
        //cs.delete(coach);
        /*for (Coach c: cs.getAll() ) {
            System.out.println(c);
        }
        System.out.println(cs.getOne(5));*/

        EventService es= new EventService();
        Event event= new Event(116,"baby updated","url_image", " cest lévenemnt du bebe","H235dfs",
                Date.valueOf("2024-04-29"),
                LocalDateTime.parse("2024-04-29T08:30:00"), // Use LocalDateTime.parse() for LocalDateTime
                LocalDateTime.parse("2024-04-29T10:00:00"), // Use LocalDateTime.parse() for LocalDateTime
                false, coach );
        //es.add(event);
        //es.update(event);
        //es.delete(event);
        /*for (Event c: es.getAll() ) {
            System.out.println(c);
        }*/
        //System.out.println(es.getOne(110));


    }
}