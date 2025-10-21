package com.mycompany.batalhatatica;
import java.util.Scanner;

public class Game{
    private Board jogo;

    public Game(){
        this.jogo = new Board();
    }

    public void bootGame() {
        Scanner teclado = new Scanner(System.in);

        boolean continuar = true;
        int turno = 1;
        System.out.print("Digite a posicao (ex:B3) e a inicial da sua peca (ex: L para Langster, ou 'sair': ");
        while(continuar) {
            jogo.display(turno);
            System.out.print("Digite a jogada: ");
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

            if (!jogo.insert(posicao, peca))
                System.out.println("Posicao invalida, tente novamente.");
        }
        teclado.close();
    }
}