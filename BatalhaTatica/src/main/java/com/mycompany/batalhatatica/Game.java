package com.mycompany.batalhatatica;
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
        System.out.print("Digite a posicao (ex:B3) e a inicial da sua peca (ex: L para Langster, ou 'sair': ");

        while(continuar) {
            jogo.display(turno);
            System.out.print("Digite a jogada 1: ");
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
            String peca = partes[1];

            teclado.nextLine();

            if (!jogo.insert(posicao, peca))
                System.out.println("Posicao invalida, tente novamente.");

            if(maquina!=null){
                jogo.display(turno);
                System.out.print("Digite a jogada 2: ");
                entrada = teclado.nextLine();
                String[] partes2 = entrada.trim().split("\\s+");
                if (partes2.length != 2) {
                    System.out.println("Entrada inválida, use o formato B3 L");
                    continue;
                }
                posicao = partes2[0];
                peca = partes2[1];

                if (!jogo.insert(posicao, peca))
                    System.out.println("Posicao invalida, tente novamente.");
            }
            else{
                maquina.jogadaAutomatica(jogo);
            }

            turno++;
        }
        teclado.close();
    }
}