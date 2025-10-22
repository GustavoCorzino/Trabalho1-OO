package com.mycompany.batalhatatica;
import java.util.Scanner;

public class Characters {
    private String name;
    private int hp;
    private double atk;
    private double def;
    private int range;
    private double crit;
    private double resist;

    public Characters(String name, int hp, double atk, double def, int range, double crit, double resist){
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
        return nome;
    }

    // Getters e Setters
    public String getName() { return name; }
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }
    public double getAtk() { return atk; }
    public double getDef() { return def; }
    public int getRange() { return range; }
    public double getCrit() { return crit; }
    public double getResist() { return resist; }

    public void starkAttacked(int linha, int coluna){ /* implementar resolução de ataque */ }
    public void lannisterAttacked(int linha, int coluna){ /* implementar resolução de ataque */ }
    public void targaryenAttacked(int linha, int coluna){ /* implementar resolução de ataque */ }
}

class Stark extends Characters {
    public Stark(String name){
        super(name, 60, 20, 10, 1, 0.0, 0.2);
    }
}

class Lannister extends Characters {
    public Lannister(String name){
        super(name, 50, 20, 10, 2, 0.15, 0.0);
    }
}

class Targaryen extends Characters {
    public Targaryen(String name){
        super(name, 45, 20, 10, 3, 1.0, 0.0);
    }
}


