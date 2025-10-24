package com.mycompany.batalhatatica;

public class Match {
    private String mover;      // "Jogador 1", "Jogador 2" ou "Máquina"
    private String jogada;     // ex: "B3 S"
    private String[][] snapshot;

    public Match(String mover, String jogada, String[][] snapshot) {
        this.mover = mover;
        this.jogada = jogada;
        this.snapshot = snapshot;
    }

    public void display(int acao) {
        if(mover.equals("Setup inicial do jogo"))
            System.out.println("Acao " + acao + " - " + mover  + jogada);
        else
            System.out.println("Acao " + acao + " - " + mover + " jogou: " + jogada);
        System.out.print("   ");
        for (char c = 'A'; c < 'A' + 10; c++) System.out.print(" " + c + " ");
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.printf("%2d ", i + 1);
            for (int j = 0; j < 10; j++) System.out.print(" " + snapshot[i][j] + " ");
            System.out.println();
        }
    }
}