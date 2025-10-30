package com.mycompany.batalhatatica;
import java.util.Scanner;

public class BatalhaTatica {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean repetir = true;
        while(repetir){
            System.out.println("Escolha seus guerreiros com os numeros respectivos e separados por espaco: Temos as opcoes 1-Stark, 2-Lannister e 3-Targaryen." +
                    "\nAs habilidades sao Vida, Ataque, Defesa, Alcance, Critico e Resistencia, separados assim:\n" +
                    "\nStark: 60 de vida, 20 de ataque, 10 de defesa, 1 de alcance, 0 de critico, 20% de reducao de dano;" +
                    "\nLannister: 50 de vida, 20 de ataque, 10 de defesa, 2 de alcance, 15% de critico, 0% de reducao de dano; " +
                    "\nTaragryen: 45 de vida, 20 de ataque, 10 de defesa, 3 de alcance, 100% de critico, 0% de reducao de dano;");

            System.out.println("Jogador 1(Azul), escolha 3 ids, separados por espaço (ex: 1 2 3):");
            String jogador = sc.nextLine();
            String[] partes = jogador.trim().split("\\s+");

            // Verificação de entrada até o usuário fornecer 3 IDs válidos
            while (partes.length != 3 || !validarEntradas(partes)) {
                System.out.println("Entrada inválida! Digite exatamente 3 números válidos, separados por espaço.");
                jogador = sc.nextLine();
                partes = jogador.trim().split("\\s+");
            }

            int id1 = Integer.parseInt(partes[0]);
            int id2 = Integer.parseInt(partes[1]);
            int id3 = Integer.parseInt(partes[2]);

            // Verificar se os ids são entre 1 e 3
            while (id1 < 1 || id1 > 3 || id2 < 1 || id2 > 3 || id3 < 1 || id3 > 3) {
                System.out.println("IDs inválidos. Use apenas 1, 2 ou 3.");
                jogador = sc.nextLine();
                partes = jogador.trim().split("\\s+");
                while (partes.length != 3 || !validarEntradas(partes)) {
                    System.out.println("Entrada inválida! Digite exatamente 3 números válidos.");
                    jogador = sc.nextLine();
                    partes = jogador.trim().split("\\s+");
                }
                id1 = Integer.parseInt(partes[0]);
                id2 = Integer.parseInt(partes[1]);
                id3 = Integer.parseInt(partes[2]);
            }
            Player player = new Player(id1, id2, id3);

            // Selecione o tipo de jogo
            System.out.println("Será JogadorxJogador(Insira Sim) ou JogadorxMaquina(Insira Nao)? ");
            String escolha = sc.nextLine().trim().toLowerCase();

            Game jogo = null;
            if(escolha.equals("sim")){
                System.out.println("Jogador 2(vermelho), escolha seus guerreiros seguindo o mesmo formato: ");
                jogador = sc.nextLine();
                String[] partes2 = jogador.trim().split("\\s+");

                while (partes2.length != 3 || !validarEntradas(partes2)) {
                    System.out.println("Entrada inválida! Digite exatamente 3 números válidos.");
                    jogador = sc.nextLine();
                    partes2 = jogador.trim().split("\\s+");
                }

                int id21 = Integer.parseInt(partes2[0]);
                int id22 = Integer.parseInt(partes2[1]);
                int id23 = Integer.parseInt(partes2[2]);

                while (id21 < 1 || id21 > 3 || id22 < 1 || id22 > 3 || id23 < 1 || id23 > 3) {
                    System.out.println("IDs inválidos. Use apenas 1, 2 ou 3.");
                    jogador = sc.nextLine();
                    partes2 = jogador.trim().split("\\s+");
                    while (partes2.length != 3 || !validarEntradas(partes2)) {
                        System.out.println("Entrada inválida! Digite exatamente 3 números válidos.");
                        jogador = sc.nextLine();
                        partes2 = jogador.trim().split("\\s+");
                    }
                    id21 = Integer.parseInt(partes2[0]);
                    id22 = Integer.parseInt(partes2[1]);
                    id23 = Integer.parseInt(partes2[2]);
                }

                Player player2 = new Player(id21, id22, id23);
                jogo = new Game(player, player2, null);
            } else {
                System.out.println("Escolha o nome da maquina(vermelha): ");
                String maquina = sc.nextLine();
                NPC machine = new NPC(maquina);
                jogo = new Game(player, null, machine);
            }
            repetir = jogo.bootGame();
        }
        sc.close();
    }

    // Método para validar se todas as entradas são números válidos
    private static boolean validarEntradas(String[] partes) {
        for (String parte : partes) {
            if (!parte.matches("\\d+")) {
                return false;
            }
        }
        return true;
    }
}
