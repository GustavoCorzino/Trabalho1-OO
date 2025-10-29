package com.mycompany.batalhatatica;

import java.util.Scanner;

public class Player {
    private Characters p1;
    private Characters p2;
    private Characters p3;
    private int lives;

    public Player(int id1, int id2, int id3) {
        this.p1 = escolha(id1, "1");
        this.p2 = escolha(id2, "2");
        this.p3 = escolha(id3, "3");
        this.lives = 3;
    }

    private Characters escolha(int id, String idx) {
        Scanner teclado = new Scanner(System.in);
        System.out.print("Escolha um nome para o seu guerreiro " + idx + ": ");
        String inputName = teclado.nextLine().trim();
        if (inputName.isEmpty())
        inputName = "Player" + idx;
        if (id == 1) 
            return new Characters.Stark(inputName);
        else if (id == 2) 
            return new Characters.Lannister(inputName);
        else 
            return new Characters.Targaryen(inputName);
    }

    public Characters getP1() { return p1; }
    public Characters getP2() { return p2; }
    public Characters getP3() { return p3; }
    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }
}