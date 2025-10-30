package com.mycompany.batalhatatica;

//Classe para organizar o bot/maquina ou NPC(Non-playable character)
public class NPC {
    private Characters p1;
    private Characters p2;
    private Characters p3;
    private int lives;

    //definir o nome dos personagens do bot
    public NPC(String namePrefix) {
        this.p1 = escolha(namePrefix + "_1", 1);
        this.p2 = escolha(namePrefix + "_2", 2);
        this.p3 = escolha(namePrefix + "_3", 3);
        this.lives = 3;
    }

    //Função para o bot escolher 3 personagens aleatórios
    private Characters escolha(String name, int idx) {
        int id = (int)(Math.random() * 3) + 1;
        if (id == 1) 
            return new Characters.Stark(name, idx);
        else if (id == 2) 
            return new Characters.Lannister(name, idx);
        else 
            return new Characters.Targaryen(name, idx);
    }

    // getters e setter
    public Characters getP1() { return p1; }
    public Characters getP2() { return p2; }
    public Characters getP3() { return p3; }
    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }
}