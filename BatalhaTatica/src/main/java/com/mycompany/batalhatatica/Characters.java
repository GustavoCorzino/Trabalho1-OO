package com.mycompany.batalhatatica;

public class Characters {
    private String name;
    private int hp;
    private double atk;
    private double def;
    private int range;
    private double crit;
    private double resist;
    private int linha;
    private int coluna;

    public Characters(String name, int hp, double atk, double def, int range, double crit, double resist, int linha, int coluna) {
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.range = range;
        this.crit = crit;
        this.resist = resist;
        this.linha = linha;
        this.coluna = coluna;
    }

    public Characters(String name, int hp, double atk, double def, int range, double crit, double resist) {
        this(name, hp, atk, def, range, crit, resist, -1, -1);
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
    public int getLinha() { return linha; }
    public int getColuna() { return coluna; }
    public void setPosition(int l, int c) { this.linha = l; this.coluna = c; }

    //verifica se pode atacar calculando antes a distancia entre eles
    public boolean podeAtacar(Characters oponente){
        int distLin = Math.abs(this.linha - oponente.getLinha());
        int distCol = Math.abs(this.coluna - oponente.getColuna());
        int dist = Math.max(distLin, distCol);
        return dist <= range;
    }

    // dano / estado
    public void receiveDamage(int dano) {this.hp -= dano;}
    public boolean isDead() { return this.hp <= 0; }

    public void starkAttacked(int linha, int coluna){ /* opcional: reações */ }
    public void lannisterAttacked(int linha, int coluna){ /* opcional: reações */ }
    public void targaryenAttacked(int linha, int coluna){ /* opcional: reações */ }
}

// Subclasses com dois construtores (com/sem posição)
class Stark extends Characters {
    public Stark(String name, int linha, int coluna){
        super(name, 60, 20, 10, 1, 1.0, 1.2, linha, coluna);
    }
    public Stark(String name) { super(name, 60, 20, 10, 1, 1.0, 1.2); }
}

class Lannister extends Characters {
    public Lannister(String name, int linha, int coluna){
        super(name, 50, 20, 10, 2, 1.15, 1.0, linha, coluna);
    }
    public Lannister(String name) { super(name, 50, 20, 10, 2, 1.15, 1.0); }
}

class Targaryen extends Characters {
    public Targaryen(String name, int linha, int coluna){
        super(name, 45, 20, 10, 3, 0.0, 1.0, linha, coluna);
    }
    public Targaryen(String name) { super(name, 45, 20, 10, 3, 0.0, 1.0); }
}


