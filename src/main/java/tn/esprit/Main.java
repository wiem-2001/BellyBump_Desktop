package tn.esprit;

import tn.esprit.entities.User;

import tn.esprit.services.UserServices;
import tn.esprit.util.MaConnexion;

import java.sql.Date;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        MaConnexion cnx = MaConnexion.getInstance();
        UserServices us=new UserServices();
        User u1=new User("wiembm00@gmail.com","mlou5eya","wiem","benmansour","bizerte","iamge", Date.valueOf("2021-04-29"),25021548);
       // us.add(u1);
        us.getOne("wiembm00@gmail.com");
       // us.delete(u1);
        User u2=new User("wiembm00@gmail.com","wiem","ben mansour","tunis",Date.valueOf("2021-04-29"),222222);
        us.update(u2);
        us.updatePassword("wiembm00@gmail.com","newp");
        us.getAll();
    }
}