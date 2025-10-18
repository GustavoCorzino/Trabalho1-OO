package com.mycompany.batalhatatica;

public class Board {
    private int[][] table;
    private String[] occupied;
    private String command1;
    private String command2;
    
    public Board() {
        this.table = new int[11][11];
        this.occupied = new String[4];
        this.command1 = "";
        this.command2 = "";
    }
    
    // Getters e Setters
    public int[][] getTable() {
        return table;
    }
    
    public String[] getOccupied() {
        return occupied;
    }
    
    public void setCommand1(String command1) {
        this.command1 = command1;
    }
    
    public void setCommand2(String command2) {
        this.command2 = command2;
    }
}