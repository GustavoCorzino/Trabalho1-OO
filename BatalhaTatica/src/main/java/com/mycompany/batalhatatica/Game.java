package com.mycompany.batalhatatica;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game{
    private Board jogo;
    private Player jogador1;
    private Player jogador2;
    private NPC maquina;

    public Game(Player jog1, Player jog2, NPC maq){
        this.jogo = new Board();
        this.jogador1 = jog1;
        this.jogador2 = jog2;
        this.maquina = maq;
    }

    public boolean bootGame() {
        Scanner teclado = new Scanner(System.in);
        List<Match> historico = new ArrayList<>();
        boolean continuar = true;
        int turno = 1;

        String col0, col1, col2;
        col0 = String.valueOf((char) ('A' + (int)(Math.random() * 10)));
        col1 = String.valueOf((char) ('A' + (int)(Math.random() * 10)));
        col2 = String.valueOf((char) ('A' + (int)(Math.random() * 10)));


        // setup jogador1 (linhas 1,2,3)
        jogo.insert(col0 + "1", jogador1.getP1(), "J1");
        jogo.insert(col1 + "2", jogador1.getP2(), "J1");
        jogo.insert(col2 + "3", jogador1.getP3(), "J1");

        // Jogador 2 / Máquina na base (linhas 10,9,8)
        if (maquina != null) {
            jogo.insert("A10", maquina.getP1(), "NPC");
            jogo.insert("B10", maquina.getP2(), "NPC");
            jogo.insert("C10", maquina.getP3(), "NPC");
        } else if (jogador2 != null) {
            col0 = String.valueOf((char) ('A' + (int)(Math.random() * 10)));
            col1 = String.valueOf((char) ('A' + (int)(Math.random() * 10)));
            col2 = String.valueOf((char) ('A' + (int)(Math.random() * 10)));
            // colocar em 10,9,8 para não sobrescrever
            jogo.insert(col0 + "10", jogador2.getP1(), "J2");
            jogo.insert(col1 + "9", jogador2.getP2(), "J2");
            jogo.insert(col2 + "8", jogador2.getP3(), "J2");
        }
        historico.add(new Match("Setup inicial do jogo", " ", jogo.getSnapshot()));

        while(continuar) {
            boolean ataque;
            jogo.display(turno, jogador1, jogador2, maquina);

            // Entrada no formato: "<S|L|T> <w|a|s|d>" ou 'sair'
            System.out.print("Jogador 1, escolha seu comando (ex: 1 w) ou 'sair': ");
            String entrada = teclado.nextLine().trim();
            if (entrada.equalsIgnoreCase("sair")) { continuar = false; break; }
            while (!entrada.contains(" ")) {
                System.out.print("Entrada inválida, separe o comando por espaço, (ex: 1 w) ou 'sair': ");
                entrada = teclado.nextLine().trim();
            }
            String[] partes = entrada.split("\\s+");
            while (partes.length != 2) {
                System.out.println("Entrada inválida. Use o formato '1 w' (peça + direção).");
                System.out.print("Jogador 1, escolha seu comando (ex: 1 w) ou 'sair': ");
                entrada = teclado.nextLine().trim();
                if (entrada.equalsIgnoreCase("sair")) { continuar = false; break; }
                partes = entrada.split("\\s+");
            } 

            String pieza = partes[0].toUpperCase();
            char dir = partes[1].toUpperCase().charAt(0);
            while (!"123".contains(pieza) || "WASD".indexOf(dir) < 0) {
                System.out.println("Formato inválido. Peça: 1/2/3 e direção: w/a/s/d.");
                System.out.print("Jogador 1, seu comando (ex: 2 w) ou 'sair': ");
                entrada = teclado.nextLine().trim();
                if (entrada.equalsIgnoreCase("sair")) { continuar = false; break; }
                partes = entrada.split("\\s+");
                pieza = partes[0].toUpperCase();
                dir = partes[1].toUpperCase().charAt(0);
            }

            Characters actor1 = null;
            if (pieza.equals("1")) actor1 = jogador1.getP1();
            else if (pieza.equals("2")) actor1 = jogador1.getP2();
            else if (pieza.equals("3")) actor1 = jogador1.getP3();

            while (actor1 == null || actor1.isDead()) {
                System.out.println("Peça inválida ou já está morta. Peça: 1/2/3 e direção: w/a/s/d.");
                System.out.print("Jogador 1, seu comando (ex: 2 w) ou 'sair': ");
                entrada = teclado.nextLine().trim();
                if (entrada.equalsIgnoreCase("sair")) { continuar = false; break; }
                partes = entrada.split("\\s+");
                pieza = partes[0].toUpperCase();
                dir = partes[1].toUpperCase().charAt(0);
                actor1 = null;
                if (pieza.equals("1")) actor1 = jogador1.getP1();
                else if (pieza.equals("2")) actor1 = jogador1.getP2();
                else if (pieza.equals("3")) actor1 = jogador1.getP3();
            }

            String res = jogo.move("J1", actor1, dir);
            boolean podeHistorico = false;
            if (res == null) {
                System.out.println("Movimento inválido (fora do tabuleiro, comando errado ou casa ocupada).");
            } else if (res.equals("NOT_PLACED")) {
                System.out.println("Peça ainda não posicionada.");
            } else {
                podeHistorico=true;
                if (res.startsWith("KILL:")) {
                    String owner = res.substring(5);
                    if ("J2".equals(owner) && jogador2 != null) jogador2.setLives(jogador2.getLives() - 1);
                    else if ("NPC".equals(owner) && maquina != null) maquina.setLives(maquina.getLives() - 1);
                }
            }

            ataque = actor1.atacar(actor1, "J1", jogador1, jogador2, maquina, jogo);
            if(podeHistorico)
                if(ataque)
                    historico.add(new Match("Jogador 1", "MOVE e ATACA para " + pieza + " " + dir, jogo.getSnapshot()));
                else
                    historico.add(new Match("Jogador 1", "MOVE " + pieza + " " + dir, jogo.getSnapshot()));

            // turno do adversário
            if (maquina != null && maquina.getLives() != 0) {
                // máquina: tenta mover uma peça aleatória até conseguir
                Characters chosen = null;
                boolean played = false;
                for (int attempts = 0; attempts < 30 && !played; attempts++) {
                    int choose = (int)(Math.random() * 3) + 1;
                    chosen = (choose == 1) ? maquina.getP1() : (choose == 2) ? maquina.getP2() : maquina.getP3();
                    if (chosen == null || chosen.isDead()) continue;
                    char[] dirs = {'W','A','S','D'};
                    char dirMaq = dirs[(int)(Math.random()*4)];
                    String resM = jogo.move("NPC", chosen, dirMaq);
                    if(resM == null) continue;
                    if (resM.equals("NOT_PLACED")) continue;
                    if (resM != null && !resM.equals("NOT_PLACED")) {
                        historico.add(new Match("Máquina", "MOVE " + (chosen instanceof Characters.Stark ? "S" : chosen instanceof Characters.Lannister ? "L" : "T") + " " + dirMaq, jogo.getSnapshot()));
                        System.out.println("Máquina moveu: " + (chosen instanceof Characters.Stark ? "S" : chosen instanceof Characters.Lannister ? "L" : "T") + " " + dirMaq);
                        if  (resM.startsWith("KILL:")) {
                            String owner = resM.substring(5);
                            if ("J1".equals(owner)) jogador1.setLives(jogador1.getLives() - 1);
                            else if ("J2".equals(owner) && jogador2 != null) jogador2.setLives(jogador2.getLives() - 1);
                        }
                        played = true;
                    }
                }
                if (!played) System.out.println("Máquina não conseguiu mover nesta rodada.");

                ataque = chosen.atacar(chosen, "NPC", jogador1, jogador2, maquina, jogo);
                if (ataque)
                    historico.add(new Match("Máquina", "MOVE e ATACA com " +
                            (chosen instanceof Characters.Stark ? "S" :
                                    chosen instanceof Characters.Lannister ? "L" : "T"), jogo.getSnapshot()));
                else
                    historico.add(new Match("Máquina", "MOVE com " +
                            (chosen instanceof Characters.Stark ? "S" :
                                    chosen instanceof Characters.Lannister ? "L" : "T"), jogo.getSnapshot()));

            }
            else if (jogador2 != null && jogador2.getLives() != 0) {
                jogo.display(turno, jogador1, jogador2, maquina);
                System.out.print("Jogador 2, seu comando (ex: 1 w) ou 'sair': ");
                entrada = teclado.nextLine().trim();
                if (entrada.equalsIgnoreCase("sair")) { continuar = false; break; }
                while (!entrada.contains(" ")) {
                    System.out.print("Entrada inválida, separe o comando por espaço, (ex: 1 w) ou 'sair': ");
                    entrada = teclado.nextLine().trim();
                }
                String[] partes2 = entrada.split("\\s+");
                while (partes2.length != 2) {
                    System.out.println("Entrada inválida. Use o formato '1 w'.");
                    System.out.print("Jogador 2, seu comando (ex: 1 w) ou 'sair': ");
                    entrada = teclado.nextLine().trim();
                    if (entrada.equalsIgnoreCase("sair")) { continuar = false; break; }
                    partes2 = entrada.split("\\s+");
                }

                String pieza2 = partes2[0].toUpperCase();
                char dir2 = partes2[1].toUpperCase().charAt(0);
                while (!"123".contains(pieza2) || "WASD".indexOf(dir2) < 0) {
                    System.out.println("Formato inválido. Peça: 1/2/3 e direção: w/a/s/d.");
                    System.out.print("Jogador 2, seu comando (ex: 3 a) ou 'sair': ");
                    entrada = teclado.nextLine().trim();
                    if (entrada.equalsIgnoreCase("sair")) { continuar = false; break; }
                    partes2 = entrada.split("\\s+");
                    pieza2 = partes2[0].toUpperCase();
                    dir2 = partes2[1].toUpperCase().charAt(0);
                }

                Characters actor2 = null;
                if (pieza2.equals("1")) actor2 = jogador2.getP1();
                else if (pieza2.equals("2")) actor2 = jogador2.getP2();
                else if (pieza2.equals("3")) actor2 = jogador2.getP3();

                if (actor2 == null || actor2.isDead()) {
                    System.out.println("Peça inválida ou já está morta. Peça: 1/2/3 e direção: w/a/s/d.");
                    System.out.print("Jogador 2, seu comando (ex: 2 w) ou 'sair': ");
                    entrada = teclado.nextLine().trim();
                    if (entrada.equalsIgnoreCase("sair")) { continuar = false; break; }
                    partes2 = entrada.split("\\s+");
                    pieza2 = partes2[0].toUpperCase();
                    dir = partes2[1].toUpperCase().charAt(0);
                    actor2 = null;
                    if (pieza2.equals("1")) actor2 = jogador1.getP1();
                    else if (pieza2.equals("2")) actor2 = jogador1.getP2();
                    else if (pieza2.equals("3")) actor2 = jogador1.getP3();
                }

                String res2 = jogo.move("J2", actor2, dir2);
                if (res2 == null) System.out.println("Movimento inválido (fora do tabuleiro, comando errado ou casa ocupada).");
                else if (res2.equals("NOT_PLACED")) System.out.println("Peça ainda não posicionada.");
                else {
                    historico.add(new Match("Jogador 2", "MOVE " + pieza2 + " " + dir2, jogo.getSnapshot()));
                    if (res2.startsWith("KILL:")) {
                        String owner = res2.substring(5);
                        if ("J1".equals(owner)) jogador1.setLives(jogador1.getLives() - 1);
                        else if ("NPC".equals(owner) && maquina != null) maquina.setLives(maquina.getLives() - 1);
                    }
                }

                ataque = actor2.atacar(actor2, "J2", jogador1, jogador2, maquina, jogo);
                if (ataque)
                    historico.add(new Match("Jogador 2", "MOVE e ATACA para " + pieza2 + " " + dir2, jogo.getSnapshot()));
                else
                    historico.add(new Match("Jogador 2", "MOVE " + pieza2 + " " + dir2, jogo.getSnapshot()));
            }

            // checar fim de jogo
            if (jogador1.getLives() <= 0) {
                System.out.println("Jogador 1 perdeu todas as vidas. Fim de jogo.");
                if(maquina!=null)
                    System.out.println("o NPC ganhou!");
                else
                    System.out.println("O Jogador 2 foi o vitorioso");
                break;
            }
            if (jogador2 != null && jogador2.getLives() <= 0) {
                System.out.println("Jogador 2 perdeu todas as vidas. Fim de jogo.");
                System.out.println("O Jogador 1 foi o vitorioso");
                break;
            }
            if (maquina != null && maquina.getLives() <= 0) {
                System.out.println("Máquina perdeu todas as vidas. Fim de jogo.");
                System.out.println("O Jogador 1 foi o vitorioso");
                break;
            }

            turno++;
        }
        String jogarNovamente;
        do{
            System.out.println("Gostaria de jogar novamente(Sim) ou ver o replay e sair(Nao)?");
            jogarNovamente =  teclado.nextLine();
            jogarNovamente = jogarNovamente.trim().toUpperCase();}
        while(!"SIM".equals(jogarNovamente) && !jogarNovamente.equals("NAO"));
        if("SIM".equals(jogarNovamente) )
            return true;

        // replay
        System.out.println("\n--- Replay da partida ---\n");
        int t = 1;
        int contagem = 1;
        Scanner pausa = new Scanner(System.in);
        for (Match m : historico) {
            System.out.println("Turno " + t);
            if(contagem%2==0)
                t++;
            m.display(contagem++);
            System.out.println();
            System.out.println("Pressione ENTER para o próximo frame...");
            pausa.nextLine();
        }

        teclado.close();
        return false;
    }
}
