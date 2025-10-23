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

    public void bootGame() {
        Scanner teclado = new Scanner(System.in);
        List<Match> historico = new ArrayList<>();
        boolean continuar = true;
        int turno = 1;

        while(continuar) {
            jogo.display(turno);
            System.out.print("Jogador 1 — Digite a posicao (ex: B3) e a inicial da sua peca (S/L/T) ou 'sair': ");
            String entrada = teclado.nextLine();
            if (entrada.equalsIgnoreCase("sair")){
                continuar = false;
                break;
            }
            String[] partes = entrada.trim().split("\\s+");
            if (partes.length != 2) {
                System.out.println("Entrada inválida, use o formato B3 L");
                continue;
            }
            String posicao = partes[0];
            String peca = partes[1].toUpperCase();

            Characters actor1 = null;
            if (peca.equals("S")) 
                actor1 = jogador2.getP1();
            else if (peca.equals("L")) 
                actor1 = jogador2.getP2();
            else if (peca.equals("T")) 
                actor1 = jogador2.getP3();

            boolean ok = (actor1 != null) && jogo.insert(posicao, peca, actor1);
            if (!ok) {
                System.out.println("Posicao/peca invalida, tente novamente.");
            } else {
                historico.add(new Match("Jogador 1", posicao + " " + peca, jogo.getSnapshot()));
            }

            // vez do oponente
            if (maquina != null){
                // máquina joga
                // jogadaAutomatica faz insert
                // jogadaAutomatica retorna boolean
                int row = (int)(Math.random() * 10) + 1; // 1..10
                char colChar = (char)('A' + (int)(Math.random() * 10)); // A..J
                String pos = "" + colChar + row;
                int choose = (int)(Math.random() * 3) + 1;
                Characters chosen = (choose == 1) ? maquina.getP1() : (choose == 2) ? maquina.getP2() : maquina.getP3();
                String letra = (chosen instanceof Stark) ? "S" : (chosen instanceof Lannister) ? "L" : "T";
                boolean okMaq = jogo.insert(pos, letra, chosen);
                if (okMaq) {
                    historico.add(new Match("Máquina", pos + " " + letra, jogo.getSnapshot()));
                    System.out.println("Máquina joga: " + pos + " " + letra);
                } else {
                    // se falhar (posição inválida/ocupada), apenas imprime e segue
                    System.out.println("Máquina tentou jogar em " + pos + " e falhou.");
                }

            } else {
                jogo.display(turno);
                System.out.print("Jogador 2 — Digite a posicao (ex: B3) e a inicial da sua peca (S/L/T) ou 'sair': ");
                entrada = teclado.nextLine();
                if (entrada.equalsIgnoreCase("sair")){
                    continuar = false;
                    break;
                }
                String[] partes2 = entrada.trim().split("\\s+");
                if (partes2.length != 2) {
                    System.out.println("Entrada inválida, use o formato B3 L");
                    continue;
                }
                posicao = partes2[0];
                peca = partes2[1].toUpperCase();
                Characters actor2 = null;
                if (peca.equals("S")) 
                    actor2 = jogador1.getP1();
                else if (peca.equals("L")) 
                    actor2 = jogador1.getP2();
                else if (peca.equals("T")) 
                    actor2 = jogador1.getP3();

                boolean ok2 = (actor2 != null) && jogo.insert(posicao, peca, actor2);
                if (!ok2) {
                    System.out.println("Posicao/peca invalida, tente novamente.");
                } else {
                    historico.add(new Match("Jogador 2", posicao + " " + peca, jogo.getSnapshot()));
                }
            }

            turno++;
        }

        // exibir replay ao final
        System.out.println("\n--- Replay da partida ---\n");
        int t = 1;
        for (Match m : historico) {
            m.display(t++);
            System.out.println();
        }

        teclado.close();
    }
}