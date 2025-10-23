package com.mycompany.batalhatatica;

public class NPC {
    private Characters p1;
    private Characters p2;
    private Characters p3;
    private int lives;

    public NPC(String namePrefix) {
        this.p1 = escolha(namePrefix + "_1");
        this.p2 = escolha(namePrefix + "_2");
        this.p3 = escolha(namePrefix + "_3");
        this.lives = 3;
    }

    private Characters escolha(String name) {
        int id = (int)(Math.random() * 3) + 1;
        if (id == 1) 
            return new Stark(name);
        else if (id == 2) 
            return new Lannister(name);
        else 
            return new Targaryen(name);
    }

    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }

    public void jogadaAutomatica(Board jogo){
        int row = (int)(Math.random() * 10) + 1; // 1..10
        char colChar = (char)('A' + (int)(Math.random() * 10)); // A..J
        String pos = "" + colChar + row;

        // escolher peça aleatória do NPC
        int choose = (int)(Math.random() * 3) + 1;
        Characters chosen = (choose == 1) ? p1 : (choose == 2) ? p2 : p3;
        String letra = (chosen instanceof Stark) ? "S" : (chosen instanceof Lannister) ? "L" : "T";

        jogo.insert(pos, letra, chosen);
        System.out.println("Máquina joga: " + pos + " " + letra);
    }

    // getters
    public Characters getP1() { return p1; }
    public Characters getP2() { return p2; }
    public Characters getP3() { return p3; }
}