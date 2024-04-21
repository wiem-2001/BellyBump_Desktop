package tn.esprit.test;

import tn.esprit.models.Comment;
import tn.esprit.models.Post;
import tn.esprit.services.CommentService;
import tn.esprit.services.PostService;
import tn.esprit.util.MaConnexion;


import java.util.List;

public class Main {
    public static void main(String[] args) {
        MaConnexion mac = MaConnexion.getInstance();
        PostService postService = new PostService();
        CommentService commentService = new CommentService();

        Comment newCom = new Comment("hiii","hello",23);
        commentService.add(newCom);
        List<Comment> allCom =commentService.getAll();
        for(Comment comment : allCom){
            System.out.println(comment);
        }
        Comment comrec=commentService.getOne(12);
        if (comrec != null)
        {
            System.out.println("recccom"+comrec);
            comrec.setAuthor("yallaaa");
            comrec.setContenu("yallaaa");
            commentService.update(comrec);
        }
        else{
            System.out.println("erreurrr");
        }



    }
}