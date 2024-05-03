package tn.esprit;
import tn.esprit.entities.Task;
import tn.esprit.entities.User;

import tn.esprit.services.TasksServices;
import tn.esprit.services.UserServices;
import tn.esprit.util.MaConnexion;
import tn.esprit.controllers.event.ShowEvents;
import tn.esprit.entities.Coach;
import tn.esprit.services.CoachService;
import tn.esprit.services.EventService;
import java.sql.Date;
public class Main {
    public static void main(String[] args) {
        MaConnexion cnx = MaConnexion.getInstance();
        UserServices us=new UserServices();
        TasksServices ts=new TasksServices();
        User u1=new User("admin","admin","admin@gmail.com","admin","admin", Date.valueOf("2021-04-29"),98765432);
        User u2=new User("wiembm200@gmail.com","password2","rihab","benmansour","bizerte", Date.valueOf("2021-04-29"),25);
      us.add(u1);
     //  us.add(u1);
       // us.getOne("wiembm00@gmail.com");
       /* User u3=new User("wiembm00@gmail.com","wiem","ben mansour","tunis",Date.valueOf("2021-04-29"),222222);
        us.update(u3);
        us.updatePassword("wiembm00@gmail.com","newp");
        List<User> userList = us.getAll();
       for (User user : userList) {
        System.out.println("User name "+user.getLast_name() + " "+user.getFirst_name()+"status : "+user.getStatus()+" birthday : "+user.getBirthday());
        }
        us.updateStatus(u2);
        us.login("wiembm00@gmail.com","password");
        us.login("wiembm00@gmail.com","passwordmm");
        us.getUserStatisticsByStatus();
       us.getUsersCreatedPerMonth();*/
        Task t=new Task(15,"i have to be done from my duties","homework", "tag");
       // Task t2=new Task(1,"i have to be done from my duties","homework");
       // Task t3=new Task(1,"i have to be done from my duties","homework", "tag",Date.valueOf("2021-04-29"));
     //  ts.add(t,239);
       // ts.add(t2,227);
          // us.delete(u2);
         // ts.update(t);
        // ts.delete(t);
     // List<Task> tasks = ts.getAllByTag("wiembm2001@gmail.com","Mother");
     // us.updateProfilImage("image2","wiembm2001@gmail.com");
      //  ts.getOne(15,226);
     //us.saveResetToken("wiembm2001@gmail.com","token");

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


     //   ShowEvents controller = new ShowEvents();
       // controller.getRecommendedEvents();

    }
}