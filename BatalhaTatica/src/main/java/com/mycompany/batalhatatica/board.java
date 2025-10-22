package com.mycompany.batalhatatica;

public class Board {
    private String[][] table;

    public Board() {
        this.table = new String[10][10];
        beginGame();
    }

    private void beginGame(){
        for(int i=0; i<10; i++)
            for(int j=0; j<10; j++)
                table[i][j] = ".";
    }

    public String getOccupied(int linha, int coluna) {
        return table[linha][coluna];
    }

    public void display(int turno){
        System.out.println("Turno " + turno);
        System.out.println();
        System.out.print("   ");
        for (char c = 'A'; c < 'A' + 10; c++) System.out.print(" " + c + " ");
        System.out.println();
        for(int i=0; i<10; i++){
            System.out.printf("%2d ", i+1);
            for(int j=0; j<10; j++) System.out.print(" " + table[i][j] + " ");
            System.out.println();
        }
    }

    // insere peça; characterObj é quem faz a jogada
    public boolean insert(String posicion, String character, Characters characterObj){
        if(posicion == null) return false;
        posicion = posicion.trim().toUpperCase();
        if(posicion.length() < 2 || posicion.length() > 3) 
            return false;
        char colunaChar = posicion.charAt(0);
        int linha;
        linha = Integer.parseInt(posicion.substring(1)) - 1;
        int coluna = colunaChar - 'A';

        if(linha<0 || linha>=10 || coluna<0 || coluna>=10)
            return false;

        if(table[linha][coluna].equals(".")) {
            table[linha][coluna] = character.toUpperCase();
        } else {
            String existing = table[linha][coluna];
            if(existing.equals("L")) 
                characterObj.lannisterAttacked(linha, coluna);
            else if(existing.equals("T")) 
                characterObj.targaryenAttacked(linha, coluna);
            else if(existing.equals("S")) 
                characterObj.starkAttacked(linha, coluna);
        }
        return true;
    }

    // retorna uma cópia do tabuleiro
    public String[][] getSnapshot() {
        String[][] copy = new String[10][10];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                copy[i][j] = table[i][j];
        return copy;
    }
}