package com.mycompany.batalhatatica;

//classe para organizar as células das tabelas
public class Cell {
    private Characters occupant; // objeto que ocupa a célula
    private String owner; // "J1", "J2" ou "NPC"

    public Cell() { this.occupant = null; this.owner = null; }

    public Characters getOccupant() { return occupant; }
    public String getOwner() { return owner; }

    public void setOccupant(Characters c, String owner) {
        this.occupant = c;
        this.owner = owner;
    }

    //limpa a célula para ser ocupada pelo "." ou por outro personagem
    public void clear() {
        this.occupant = null;
        this.owner = null;
    }

    //impressão das letras/perosnagens no código
    public String displayChar() {
        //strings para mudança de cores das letras, azul para J1 e vermelho para J2
        String red = "\u001B[31m";
        String blue = "\u001B[34m";
        String reset = "\u001B[0m";
        if (occupant == null) return ".";
        if (occupant instanceof Characters.Stark){
            if("J1".equals(owner))
                return blue + "S" + reset;
            else
                return red + "S" + reset;}
        if (occupant instanceof Characters.Lannister){
            if("J1".equals(owner))
                return blue + "L" + reset;
            else
                return red + "L" + reset;}
        if(occupant instanceof Characters.Targaryen){
            if("J1".equals(owner))
                return blue + "T" + reset;
            else
                return red + "T" + reset;}
        return " ";
    }
}