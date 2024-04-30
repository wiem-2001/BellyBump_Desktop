package tn.esprit.models;

public class Comment {
    private int id, postId;
    private String contenu, author;

    public Comment() {
    }

    public Comment(int id, String author, String contenu, int postId) {
        this.id = id;
        this.author = author;
        this.contenu = contenu;
        this.postId = postId;
    }

    public Comment(String author, String contenu, int postId) {
        this.author = author;
        this.contenu = contenu;
        this.postId = postId;
    }

    public Comment(String author, String contenu) {
        this.author = author;
        this.contenu = contenu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        if(contenu == null ||contenu.trim().length()<2)
        {
            throw new IllegalArgumentException("Le contenu doit etre de longuer minimal de 2 caractères");
        }
        this.contenu = contenu;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if(author == null ||author.trim().length()<2)
        {
            throw new IllegalArgumentException("Le nom d'auteur doit etre de longuer minimal de 2 caractères");
        }
        this.author = author;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", contenu='" + contenu + '\'' +
                ", postId=" + postId +
                '}';
    }
}
