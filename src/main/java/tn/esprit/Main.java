package tn.esprit;

import tn.esprit.entities.Coach;
import tn.esprit.entities.Partenaire;
import tn.esprit.entities.Task;
import tn.esprit.services.*;
import tn.esprit.entities.Produit;
import tn.esprit.util.MaConnexion;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        //MaConnexion cnx = MaConnexion.getInstance();
    /*    PartenaireServices ps= new PartenaireServices();
        Partenaire partenaire =new Partenaire("momo" , "pampers", "khaledgg@gmail.com","marque de bébé");
        //ps.Insert(partenaire);
        System.out.println(ps.getAll());
        ///p**********************************************************************************our l update
       // Partenaire partenaire = new Partenaire();
        partenaire.setId(1); // The ID of the record you wish to update
        partenaire.setNom("ka");
        partenaire.setMarque("lolo");
        partenaire.setEmail("updated.email@example.com");
        partenaire.setDescription("maaaaaaa");

        ps.update(partenaire);

        //pour supprimerrr*************************************************************
        Partenaire p =new Partenaire();
        p.setNom("ka");
        ps.delete(p);




        //////////POUR PRODUIT
        ProduitServices  pds= new ProduitServices();
       // Produit prod1 = new Produit("perfume","perfums for baby boys",12.2,10);
        //pds.add(prod1);

        System.out.println(pds.getAll());
        Produit produit = new Produit();
        produit.setNom("perfume");
       // pds.augmenterStock(3,2);
       // pds.diminuerStock(3,2);
       //pds.acheterProduitAvecPromotion(3,3);
       // pds.delete(produit);
        UserServices us=new UserServices();
        TasksServices ts=new TasksServices();
        //  User u1=new User("admin","admin","admin@gmail.com","admin","admin", Date.valueOf("2021-04-29"),98765432);
        //  User u2=new User("wiembm200@gmail.com","password2","rihab","benmansour","bizerte", Date.valueOf("2021-04-29"),25);
        //  us.add(u1);
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
        // CartServices cr=new CartServices();
        //System.out.println(cr.getAll());*/
        Task t=new Task(15,"i have to be done from my duties","homework", "tag");
        TasksServices ts=new TasksServices();
        ts.add(t,1);
        // Task t2=new Task(1,"i have to be done from my duties","homework");
        // Task t3=new Task(1,"i have to be done from my duties","homework", "tag",Date.valueOf("2021-04-29"));

        // ts.add(t2,227);
        // us.delete(u2);
        // ts.update(t);
        // ts.delete(t);
        // List<Task> tasks = ts.getAllByTag("wiembm2001@gmail.com","Mother");
        // us.updateProfilImage("image2","wiembm2001@gmail.com");
        //  ts.getOne(15,226);
        //us.saveResetToken("wiembm2001@gmail.com","token");
        //CoachService cs=new CoachService();
        // Coach coach=new Coach(8,12345678,"eya","lamouri","psy","eya.lamouri@esprit.tn");
        //cs.add(coach);
        //cs.update(coach);
        //cs.delete(coach);
        /*for (Coach c: cs.getAll() ) {
            System.out.println(c);
        }
        System.out.println(cs.getOne(5));*/
        // EventService es= new EventService();
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
