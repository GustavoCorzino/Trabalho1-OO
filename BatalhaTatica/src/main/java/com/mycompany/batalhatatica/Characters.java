package com.mycompany.batalhatatica;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

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
    private boolean podeAtacar(Characters oponente) {
        int distLin = Math.abs(this.linha - oponente.getLinha());
        int distCol = Math.abs(this.coluna - oponente.getColuna());
        int dist = Math.max(distLin, distCol);
        return dist <= this.range;
    }

    private Characters definirAlvo(String player, Characters atacante, Player jogador1, Player jogador2, NPC maquina) {
        List<Characters> inimigos = new ArrayList<>();

        if ("J1".equals(player)) {
            if (jogador2 != null) {
                inimigos.add(jogador2.getP1());
                inimigos.add(jogador2.getP2());
                inimigos.add(jogador2.getP3());
            } else if (maquina != null) {
                inimigos.add(maquina.getP1());
                inimigos.add(maquina.getP2());
                inimigos.add(maquina.getP3());
            }
        } else if ("J2".equals(player) || "NPC".equals(player)) {
            inimigos.add(jogador1.getP1());
            inimigos.add(jogador1.getP2());
            inimigos.add(jogador1.getP3());
        }

        List<Characters> noAlcance = new ArrayList<>();
        //filtra inimigos ao alcance
        for (Characters inimigo : inimigos) {
            if (inimigo == null || inimigo.isDead() == true) continue;
            if (atacante.podeAtacar(inimigo)) {
                noAlcance.add(inimigo);
            }
        }
        if (noAlcance.isEmpty()) return null;

        //escolher o alvo
        Characters alvo;
        if (noAlcance.size() == 1) {
            alvo = noAlcance.get(0);
        } else {
            if ("NPC".equals(player)) {
            alvo = null;
            }  //falta fazer o bot escolher o alvo;

            else {
                System.out.println("Inimigos no alcance: ");
                for (int i = 0; i < noAlcance.size(); i++) {
                    Characters inimigo = noAlcance.get(i);
                    System.out.println(i + 1 + "- " + inimigo.getName() +
                            "\nVida: " + inimigo.getHp() +
                            "\nDefesa: " + inimigo.getDef() +
                            "\nPosicao: " + inimigo.getLinha() + inimigo.getColuna());
                }
                int escolha = -1;
                String entrada;
                Scanner tec = new Scanner(System.in);
                while (escolha < 1 || escolha > noAlcance.size()) {
                    System.out.println("Escolha o inimigo que " + atacante.getName() + " ira atacar:");
                    entrada = tec.nextLine().trim();
                    if (entrada.matches("\\d+")) {//verificar se é um numero
                        escolha = Integer.parseInt(entrada);
                    } else {
                        System.out.println("Entrada invalida. Digite apenas o numero do inimigo.");
                    }
                }
                alvo = noAlcance.get(escolha - 1);
            }

        }
        return alvo;
    }

    //calculo do dano
    private double calcDamage(Characters atk, Characters def) {
        double base = atk.getAtk();
        //calculando dano base a partir dos modificadores
        if (atk instanceof Lannister) {
            base += base * atk.getCrit();
        }
        if (def instanceof Stark) {
            base -= base * def.getResist();
        }
        //calculando dano liquido
        double danoLiquido;
        if (atk instanceof Targaryen) {
            danoLiquido = base;
        } else {
            danoLiquido = base - def.getDef();
        }
        //dano apenas positivo
        danoLiquido = Math.max(0, danoLiquido);
        //formata o dano para uma casa decimal e depois transforma em double
        return Double.parseDouble(String.format("%.2f", danoLiquido));
    }

    // dano / estado
    private void receiveDamage(double dano) {
        this.hp -= dano;
        if (this.hp <= 0) this.hp = 0;
        //decidindo se vai ter impressao aqui ou na funcao ataca
        System.out.println(this.name + "recebeu " + dano + " de dano!" +
                "\nHp atual: " + this.hp);
    }

    public boolean isDead() {
        return this.hp <= 0;
    }

    /*public void atacar(Character actor, String player, Player jogador1, Player jogador2, NPC maquina) { // ex: jogador1.getP1().atacar("J1");
        Characters atacante = actor;
        Characters alvo = definirAlvo(player, atacante, jogador1, jogador2, maquina);

        if (alvo == null) {
            System.out.println("Nenhum oponente ao alcance de " + atacante.getName() + ". Turno encerrado.");
            return null;
        }

        double damage = calcDamage(atacante, alvo);
        alvo.receiveDamage(damage);
        boolean morto = alvo.isDead();
    }*/

    // Subclasses com dois construtores (com/sem posição)
    public static class Stark extends Characters {
        public Stark(String name, int linha, int coluna) {
            super(name, 60, 20, 10, 1, 1.0, 0.2, linha, coluna);
        }

        public Stark(String name) {
            super(name, 60, 20, 10, 1, 1.0, 0.2);
        }
    }

    public static class Lannister extends Characters {
        public Lannister(String name, int linha, int coluna) {
            super(name, 50, 20, 10, 2, 0.15, 1.0, linha, coluna);
        }

        public Lannister(String name) {
            super(name, 50, 20, 10, 2, 0.15, 1.0);
        }
    }

    public static class Targaryen extends Characters {
        public Targaryen(String name, int linha, int coluna) {
            super(name, 45, 20, 10, 3, 0.0, 1.0, linha, coluna);
        }

        public Targaryen(String name) {
            super(name, 45, 20, 10, 3, 0.0, 1.0);
        }
    }

}
