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
        if (occupant == null) return ".";
        if (occupant instanceof Stark) return "S";
        if (occupant instanceof Lannister) return "L";
        return "T";
    }
}