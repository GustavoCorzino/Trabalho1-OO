package com.mycompany.batalhatatica;
import java.util.Scanner;

//classe de base para os personagens com, vida, dano, defesa, alcance, mod. ofensivo e mod. defensivo
public class Characters{
    private int id;
    private String name;
    private int hp;
    private double atk;
    private double def;
    private int range;
    private double crit;
    private double resist;

    public Characters(int id, String name, int hp, double atk, double def, int range, double crit, double resist){
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.range = range;
        this.crit = crit;
        this.resist = resist;
    }

    public String nomezinho(String name){
        Scanner teclado = new Scanner(System.in);
        System.out.print("Digite o nome do seu " + name + ": ");
        String nome = teclado.nextLine();
        teclado.close();
        return nome;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
    public double getAtk() { return atk; }
    public double getDef() { return def; }
    public int getRange() { return range; }
    public double getCrit() { return crit; }
    public double getResist() { return resist; }
}

class Stark extends Characters{
    public Stark(String name) {
        super(1, name, 60, 20, 10, 1, 1, 0.8);
    }
}

class Lannister extends Characters{
    public Lannister(String name) {
        super(2, name, 50, 20, 10, 2, 1.15, 1);
    }
}

class Targaryen extends Characters{
    public Targaryen(String name){
        super(3, name, 45, 20, 10, 3, 0, 1);
    }
}


