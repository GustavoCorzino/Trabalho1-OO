package com.mycompany.batalhatatica;

public class Player extends Characters{
    private Characters p1;
    private Characters p2;
    private Characters p3;
    private int lives;
    
    public Player(int id1, int id2, int id3) {
        super(0, "default", 0, 0, 0, 0, 0, 0); // Valores padrão para Player
        String name = "";
        this.p1 = escolha(id1, nomezinho(name));
        this.p2 = escolha(id2, nomezinho(name));
        this.p3 = escolha(id3, nomezinho(name));
        this.lives = 3;
    }

    private Characters escolha(int id, String name) {
        if(id == 1)
            return new Stark(name);
        else if(id == 2)
            return new Lannister(name);
        else if(id == 3)
            return new Targaryen(name);
        else
            return 0;
    }
    
    // Getters e Setters
    public Characters getP1() { return p1; }
    public Characters getP2() { return p2; }
    public Characters getP3() { return p3; }
    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }
}