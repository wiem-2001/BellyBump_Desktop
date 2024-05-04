package tn.esprit.entities;



public class Post {
    //Attribut
    private int id;
    private String title,auteur,content,image;


    //constructor

    public Post() {

    }

    public Post( int id,String title, String auteur, String content, String image) {
        this.title = title;
        this.auteur = auteur;
        this.content = content;
        this.image = image;
        this.id = id;

    }
    public Post(String title, String auteur, String content, String image) {
        this.title = title;
        this.auteur = auteur;
        this.content = content;


        this.image = image;
    }

//Getters and Setters


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title == null ||title.trim().length()<2)
        {
            throw new IllegalArgumentException("Le titre doit etre de longuer minimal de 2 caractères");
        }
        this.title = title;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        if(auteur == null ||auteur.trim().length()<2)
        {
            throw new IllegalArgumentException("Le nom d'auteur doit etre de longuer minimal de 2 caractères");
        }
        this.auteur = auteur;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if(content == null ||content.trim().length()<2)
        {
            throw new IllegalArgumentException("Le contenu doit etre de longuer minimal de 2 caractères");
        }
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




    //Display

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", auteur='" + auteur + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", id=" + id +
                '}';
    }
}
