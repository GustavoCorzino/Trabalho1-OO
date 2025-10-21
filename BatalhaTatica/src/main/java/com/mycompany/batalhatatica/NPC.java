package com.mycompany.batalhatatica;

public class NPC extends Characters {
    private Characters p1;
    private Characters p2;
    private Characters p3;
    private int lives;
    
    public NPC(String name) {
        super(0, name, 0, 0, 0, 0, 0, 0);
        this.p1 = escolha();
        this.p2 = escolha();
        this.p3 = escolha();
        this.lives = 3;
    }

    private Characters escolha() {
        double random = Math.random();
        int id = (int)(random * 3) + 1; // Gera 1, 2 ou 3
        
        if(id == 1)
            return new Stark("NPC Stark");
        else if(id == 2)
            return new Lannister("NPC Lannister");
        else
            return new Targaryen("NPC Targaryen");
    }
    
    public jogadaAutomatica(Board jogo){
        double random = Math.random();
        int id = (int)(random * 3) + 1; // Gera 1, 2 ou 3

        if(id == 1){
            int linha=(int)(random*1) + 'A';
            int coluna=(int)(random*1);
            String posicao=String.valueOf(linha)+String.valueOf(coluna);
            jogo.inserirPeca(posicao, "S")
        }
        else if(id == 2)
            return new Lannister("NPC Lannister");
        else
            return new Targaryen("NPC Targaryen");
    }
    
    // Getters
    public Characters getP1() { return p1; }
    public Characters getP2() { return p2; }
    public Characters getP3() { return p3; }
    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }
}