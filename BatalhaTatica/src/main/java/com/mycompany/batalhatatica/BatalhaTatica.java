package com.mycompany.batalhatatica;
import java.util.Scanner;
public class BatalhaTatica {

    public static void main(String[] args) {
        System.out.println("Escolha seus guerreiros com os numeros respectivos e separados por espaco: Temos as opcoes 1-Stark, 2-Lannister e 3-Targaryen." +
                "\nAs habilidades sao Vida, Ataque, Defesa, Alcance, Critico e Resistencia, separados assim:\n" +
                "\nStark: 60 de vida, 20 de ataque, 10 de defesa, 1 de alcance, 0 de critico, 20% de reducao de dano;" +
                "\nLannister: 50 de vida, 20 de ataque, 10 de defesa, 2 de alcance, 15% de critico, 0% de reducao de dano; " +
                "\nTaragryen: 45 de vida, 20 de ataque, 10 de defesa, 3 de alcance, 100% de critico, 0% de reducao de dano;");
        Scanner sc = new Scanner(System.in);
        System.out.println("Jogador 1(Azul), escolha 3 ids, separados por espaço (ex: 1 2 3):");
        String jogador = sc.nextLine();
        String[] partes = jogador.trim().split("\\s+");
        while (partes.length != 3) {
            System.out.println("Entrada inválida! Digite exatamente 3 números separados por espaço.");
            jogador = sc.nextLine();
            partes = jogador.trim().split("\\s+");
            sc.close();
            return;}
        int id1=Integer.parseInt(partes[0]);
        int id2=Integer.parseInt(partes[1]);
        int id3=Integer.parseInt(partes[2]);
        if (id1 < 1 || id1 > 3 || id2 < 1 || id2 > 3 || id3 < 1 || id3 > 3) {
            System.out.println("IDs inválidos. Use apenas 1, 2 ou 3.");
            sc.close();
            return;}
        Player player = new Player(id1, id2, id3);

        System.out.println("Será JogadorxJogador(Insira Sim) ou JogadorxMaquina(Insira Nao)? ");
        String escolha = sc.nextLine().trim().toLowerCase();

        Game jogo = null;
        if(escolha.equals("sim")){
            System.out.println("Jogador 2(vermelho), escolha seus guerreiros seguindo o mesmo formato: ");
            jogador = sc.nextLine();
            String[] partes2 = jogador.trim().split("\\s+");
            while (partes2.length != 3) {
                System.out.println("Entrada inválida! Digite exatamente 3 números separados por espaço.");
                jogador = sc.nextLine();
                partes2 = jogador.trim().split("\\s+");
                sc.close();
                return;}
            id1=Integer.parseInt(partes[0]);
            id2=Integer.parseInt(partes[1]);
            id3=Integer.parseInt(partes[2]);
            while (id1 < 1 || id1 > 3 || id2 < 1 || id2 > 3 || id3 < 1 || id3 > 3) {
                System.out.println("IDs inválidos. Use apenas 1, 2 ou 3.");
                jogador = sc.nextLine();
                partes2 = jogador.trim().split("\\s+");
                sc.close();
                return;}
            Player player2 = new Player(id1, id2, id3);
            jogo = new Game(player, player2, null);
        }
        else{
            System.out.println("Escolha o nome da maquina(vermelha): ");
            String maquina = sc.nextLine();
            NPC machine = new NPC(maquina);
            jogo = new Game(player, null, machine);
        }
        while(jogo.bootGame()){
            //repete enquanto o jogador quiser jogar novamente
        }
        sc.close();
    }
}
