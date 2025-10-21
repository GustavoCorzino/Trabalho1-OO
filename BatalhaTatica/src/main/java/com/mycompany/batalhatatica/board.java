package com.mycompany.batalhatatica;

public class Board {
    private String[][] table;
    private String[] occupied;
    
    public Board() {
        this.table = new String[11][11];
        beginGame();
        this.occupied = new String[6];
    }
    
    // Getters e Setters
    public String[][] getTable() {
        return table;
    }
    
    public String[] getOccupied() {
        return occupied;
    }

    private void beginGame(){
        for(int i=0; i<10; i++)
            for(int j=0; j<10; j++)
                table[i][j] = ".";}

    public void display(int turno){
        System.out.println("Turno " + turno);
        System.out.println();
        // Cabeçalho com letras
        System.out.print("   ");
        for (char c = 'A'; c < 'A' + 10; c++) {
            System.out.print(" " + c + " ");
        }
        System.out.println();
        for(int i=0; i<10; i++){
            System.out.printf("%2d ", i+1); //número da linha 1-10
            for(int j=0; j<10; j++){
                System.out.print(" " +table[i][j] + " ");}
            System.out.println();}}

    public boolean insert(String posicion, String character){
        if(posicion.length() < 2 || posicion.length() > 3)
            return false;
        char colunaChar=Character.toUpperCase(posicion.charAt(0));
        int linha = Integer.parseInt(posicion.substring(1))-1;
        int coluna = colunaChar - 'A';

        if(linha<0 || linha>=10 || coluna <0 || coluna>=10)
            return false;
        if(table[linha][coluna] == ".")
            table[linha][coluna] = character;
        else
            if(table[linha][coluna]=="L")
                character.langsterAtacked();
            else if(table[linha][coluna]=="T")
                character.targaryenAtacked();
            else
                character.starkAtacked();
        return true;
    }
}