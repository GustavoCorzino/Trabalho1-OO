package com.mycompany.batalhatatica;
import java.util.Scanner;
public class BatalhaTatica {

    public static void main(String[] args) {
        System.out.println("Escolha seus guerreiros com os numeros respectivos e separados por espaco: Temos as opcoes 1-Stark, 2-Lannister e 3-Targaryen." +
                "\nAs habilidades sao Vida, Ataque, Defesa, Alcance, Critico e Resistencia, separados assim:\n" +
                "Stark: 60 de vida, 20 de ataque, 10 de defesa, 1 de alcance, 0 de critivo, 20% de reducao de dano;" +
                "Lannister: 50 de vida, 20 de ataque, 10 de defesa, 2 de alcance, 15% de critivo, 0% de reducao de dano; " +
                "Taragryen: 45 de vida, 20 de ataque, 10 de defesa, 3 de alcance, 100% de critivo, 0% de reducao de dano;")
        Scanner sc = new Scanner(System.in);
        String jogador = sc.nextLine();
        String[] partes = jogador.trim().split("\\s+");
        int id1=Integer.parseInt(partes[0]);
        int id2=Integer.parseInt(partes[1]);
        int id3=Integer.parseInt(partes[2]);
        Player player = new Player(id1, id2, id3);
        System.out.println("Será JogadorxJogador(Insira Sim) ou JogadorxMaquina(Insira Nao)? ");
        sc.nextLine();
        String escolha = sc.nextLine();
        escolha = escolha.toLowerCase();
        sc.nextLine();
        if(escolha.equals("sim")){
            System.out.println("Jogador 2 escolha seus guerreiros seguindo o mesmo formato: ");
            jogador = sc.nextLine();
            String[] partes2 = jogador.trim().split("\\s+");
            id1=Integer.parseInt(partes[0]);
            id2=Integer.parseInt(partes[1]);
            id3=Integer.parseInt(partes[2]);
            Player player2 = new Player(id1, id2, id3);
            Game jogo = new Game(player, player2, null);
        }
        else{
            System.out.println("Escolha o nome da maquina: ");
            String maquina = sc.nextLine();
            NPC maquina = new NPC(maquina);
            Game jogo = new Game(player, null, maquina);
        }
        jogo.bootGame();
    }
}
