package tn.esprit.Controllers;

public enum Reaction {
    NON(0,"Like","/Images/aa-removebg-preview.png"),
    Like(1,"Like","/Images/like-removebg-preview.png"),
    Dislike(2,"Dislike","/Images/dislike-removebg-preview.png");
    private int id;
    private String name;
    private String imgSrc;

    Reaction(int id, String name, String imgSrc) {
        this.id = id;
        this.name = name;
        this.imgSrc = imgSrc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgSrc() {
        return imgSrc;
    }
}
