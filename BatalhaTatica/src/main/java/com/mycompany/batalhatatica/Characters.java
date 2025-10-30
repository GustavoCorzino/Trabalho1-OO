package com.mycompany.batalhatatica;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

//classe para os personagens
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
    private int ordem;

    //construtor básico
    public Characters(String name, int hp, double atk, double def, int range, double crit, double resist, int linha, int coluna, int ordem) {
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.range = range;
        this.crit = crit;
        this.resist = resist;
        this.linha = linha;
        this.coluna = coluna;
        this.ordem = ordem;
    }

    //construtor sem posição definida para ajudar a criar os personagens que não tiveram posições definidas
    public Characters(String name, int hp, double atk, double def, int range, double crit, double resist, int ordem) {
        this(name, hp, atk, def, range, crit, resist, -1, -1, ordem);
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
    public int getOrdem() { return ordem; }
    public void setOrdem(int ord) {this.ordem = ord;}

    //verifica se pode atacar calculando antes a distancia entre eles
    private boolean podeAtacar(Characters oponente) {
        int distLin = Math.abs(this.linha - oponente.getLinha());
        int distCol = Math.abs(this.coluna - oponente.getColuna());
        int dist = Math.max(distLin, distCol);
        return dist <= this.range;
    }

    //função auxiliar da atacar para definir os alvos do atacante
    private Characters definirAlvo(String player, Characters atacante, Player jogador1, Player jogador2, NPC maquina) {
        List<Characters> inimigos = new ArrayList<>();

        //cria a lista de inimigos do jogador
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
            //cria a lista de inimigos do J1 ou do NPC
        } else if ("J2".equals(player) || "NPC".equals(player)) {
            inimigos.add(jogador1.getP1());
            inimigos.add(jogador1.getP2());
            inimigos.add(jogador1.getP3());
        }

        //lista para guardar os personagens que estão no alcance do atacante
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
                int id = (int) (Math.random() * noAlcance.size());
                alvo = noAlcance.get(id);
                return alvo;
            }
            else {
                System.out.println("Inimigos no alcance: ");
                for (int i = 0; i < noAlcance.size(); i++) {
                    Characters inimigo = noAlcance.get(i);
                    System.out.println(i + 1 + "- " + inimigo.getName() +
                            "\nVida: " + inimigo.getHp() +
                            "\nDefesa: " + inimigo.getDef() +
                            "\nPosicao: " + 'A' + inimigo.getColuna() + " " + inimigo.getLinha());
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
        return Double.parseDouble(String.format(Locale.US, "%.2f", danoLiquido));
    }

    // dano / estado
    private void receiveDamage(double dano) {
        this.hp -= dano;
        if (this.hp <= 0) this.hp = 0;
    }

    public boolean isDead() {
        return this.getHp() <= 0;
    }


    //Função principal de ataques e confrontos
    public boolean atacar(Characters actor, String player, Player jogador1, Player jogador2, NPC maquina, Board jogo) {
        Characters atacante = actor;
        Characters alvo = definirAlvo(player, atacante, jogador1, jogador2, maquina);

        if (alvo == null) {
            System.out.println("Nenhum oponente ao alcance de " + atacante.getName() + ". Turno encerrado.");
            return false;
        }

        double damage = calcDamage(atacante, alvo);

        alvo.receiveDamage(damage);
        System.out.println(alvo.getName() + ", recebeu " + damage + " de dano!" +
                "\nHp atual: " + alvo.getHp());
        boolean morto = alvo.isDead();
        if (morto) {
            // Descobre de quem era o personagem morto
            String ownerAlvo = jogo.getCell(alvo.getLinha(),alvo.getColuna()).getOwner();

            System.out.println(alvo.getName() + " foi morto por " + atacante.getName());
            jogo.removeCharacter(alvo);

            if ("J1".equals(ownerAlvo)) {
                jogador1.setLives(jogador1.getLives() - 1);
                System.out.println("Uma vida do Jogador 1 foi perdida!");
            } else if ("J2".equals(ownerAlvo) && jogador2 != null) {
                jogador2.setLives(jogador2.getLives() - 1);
                System.out.println("Uma vida do Jogador 2 foi perdida!");
            } else if ("NPC".equals(ownerAlvo) && maquina != null) {
                maquina.setLives(maquina.getLives() - 1);
                System.out.println("A Máquina perdeu uma vida!");
            }
        }
    return true;
    }

    // Subclasses com dois construtores (com/sem posição)
    public static class Stark extends Characters {
        public Stark(String name, int linha, int coluna, int ordem) {
            super(name, 60, 20, 10, 1, 1.0, 0.2, linha, coluna, ordem);
        }

        public Stark(String name, int ordem) {
            super(name, 60, 20, 10, 1, 1.0, 0.2, 0, 0, ordem);
        }
    }

    public static class Lannister extends Characters {
        public Lannister(String name, int linha, int coluna, int ordem) {
            super(name, 50, 20, 10, 2, 0.15, 1.0, linha, coluna, ordem);
        }

        public Lannister(String name, int ordem) {
            super(name, 50, 20, 10, 2, 0.15, 1.0, 0, 0, ordem);
        }
    }

    public static class Targaryen extends Characters {
        public Targaryen(String name, int linha, int coluna, int ordem) {
            super(name, 45, 20, 10, 3, 0.0, 1.0, linha, coluna, ordem);
        }

        public Targaryen(String name, int ordem) {
            super(name, 45, 20, 10, 3, 0.0, 1.0, 0, 0, ordem);
        }
    }

}