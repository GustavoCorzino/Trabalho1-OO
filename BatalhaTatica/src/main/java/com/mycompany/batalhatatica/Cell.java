package com.mycompany.batalhatatica;

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

    public void clear() {
        this.occupant = null;
        this.owner = null;
    }

    public String displayChar() {
        String red = "\u001B[31m";
        String blue = "\u001B[34m";
        String reset = "\u001B[0m";
        if (occupant == null) return ".";
        if (occupant instanceof Characters.Stark){
            if("J1".equals(owner))
                return blue + "1" + reset;
            else
                return red + "1" + reset;}
        if (occupant instanceof Characters.Lannister){
            if("J1".equals(owner))
                return blue + "2" + reset;
            else
                return red + "2" + reset;}
        if(occupant instanceof Characters.Targaryen){
            if("J1".equals(owner))
                return blue + "3" + reset;
            else
                return red + "3" + reset;}
        return " ";
    }
}