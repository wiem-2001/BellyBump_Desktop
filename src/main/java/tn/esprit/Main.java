package tn.esprit;

import tn.esprit.entities.Task;
import tn.esprit.entities.User;

import tn.esprit.services.TasksServices;
import tn.esprit.services.UserServices;
import tn.esprit.util.MaConnexion;

import java.sql.Date;
import java.util.List;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        MaConnexion cnx = MaConnexion.getInstance();
        UserServices us=new UserServices();
        TasksServices ts=new TasksServices();
        User u1=new User("admin","admin","admin@gmail.com","admin","admin","image", Date.valueOf("2021-04-29"),98765432);
        User u2=new User("wiembm2001@gmail.com","password2","rihab","benmansour","tunis", Date.valueOf("2021-04-29"),12345678);
      //  us.add(u2);
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
       ts.add(t,239);
       // ts.add(t2,227);
          // us.delete(u2);
         // ts.update(t);
        // ts.delete(t);
     // List<Task> tasks = ts.getAllByTag("wiembm2001@gmail.com","Mother");
     // us.updateProfilImage("image2","wiembm2001@gmail.com");
      //  ts.getOne(15,226);
     us.saveResetToken("wiembm2001@gmail.com","token");
    }
}