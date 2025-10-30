package com.mycompany.batalhatatica;

//classe para definir o tabuleiro
public class Board {
    private Cell[][] table;

    public Board() {
        this.table = new Cell[10][10];
        beginGame();
    }

    public Cell getCell(int linha, int coluna){
        return this.table[linha][coluna];
    }

    private void beginGame(){
        for(int i=0; i<10; i++)
            for(int j=0; j<10; j++)
                table[i][j] = new Cell();
    }

    public String getOccupied(int linha, int coluna) {
        return table[linha][coluna].displayChar();
    }

    //Função para mostrar o tabuleiro de cada turno
    public void display(int turno, Player p1, Player p2, NPC maq){
        System.out.println("Turno " + turno);
        System.out.println();
        System.out.print("   ");
        for (char c = 'A'; c < 'A' + 10; c++) System.out.print(" " + c + " ");
        System.out.println();
        for(int i=0; i<10; i++){
            System.out.printf("%2d ", i+1);
            for(int j=0; j<10; j++) System.out.print(" " + table[i][j].displayChar() + " ");
            System.out.println();
        }

        System.out.println();
        // Mostrar progresso do Jogador 1
        System.out.println("Jogador 1 (Azul), progresso das peças:");
        printCharacterProgress(p1.getP1());
        printCharacterProgress(p1.getP2());
        printCharacterProgress(p1.getP3());

        System.out.println();

        // Mostrar progresso do Jogador 2 ou Máquina
        if (maq != null) {
            System.out.println("Máquina (Vermelho), progresso das peças:");
            printCharacterProgress(maq.getP1());
            printCharacterProgress(maq.getP2());
            printCharacterProgress(maq.getP3());
        } else if (p2 != null) {
            System.out.println("Jogador 2 (Vermelho), progresso das peças:");
            printCharacterProgress(p2.getP1());
            printCharacterProgress(p2.getP2());
            printCharacterProgress(p2.getP3());
        }

        System.out.println();
    }

    // Função para imprimir atributos dos personagens
    private void printCharacterProgress(Characters ch) {
        if (ch == null) return;
        String tipo = (ch instanceof Characters.Stark) ? "Stark" : (ch instanceof Characters.Lannister) ? "Lannister" : "Targaryen";
        String nome = ch.getName();
        int hp = ch.getHp();
        double atk = ch.getAtk();
        double def = ch.getDef();
        int range = ch.getRange();
        double crit = ch.getCrit();
        double resist = ch.getResist();
        char c = (char)('A' + ch.getColuna());
        int l = ch.getLinha() + 1;
        String pos = "" + c + l;
        int ord = ch.getOrdem();
        if(hp<=0)
            System.out.printf(nome + " (" + tipo + ") está morto...\n");
        else
            System.out.printf(nome + " (" + tipo + ") | HP: " + hp + "| Atk: " + atk + " | Def: " + def + " | Alc: " + range + " | Crit: " + crit + " | Resist: " + resist + " | Pos: " + pos + " | Ordem: " + ord + "%n" );
    }

    // Função para inserir um personagem em outra casa
    public String insert(String posicion, Characters attacker, String attackerId){
        if(posicion == null) return null;
        posicion = posicion.trim().toUpperCase();
        if(posicion.length() < 2 || posicion.length() > 3) return null;
        char colunaChar = posicion.charAt(0);
        int linha;
        linha = Integer.parseInt(posicion.substring(1)) - 1;
        int coluna = colunaChar - 'A';
        if(linha<0 || linha>=10 || coluna<0 || coluna>=10) return null;

        Cell cell = table[linha][coluna];
        if (cell.getOccupant() == null) {
            cell.setOccupant(attacker, attackerId);
            attacker.setPosition(linha, coluna);
            return "PLACED";//posicao valida, boneco foi inserido
        } else {
            return null;//posição invalida
        }
    }

    // Função principal de movimentação dos personagens
    public String move(String ownerId, Characters piece, char direction) {
        int l = piece.getLinha();
        int c = piece.getColuna();
        if (l < 0 || c < 0) return "NOT_PLACED";//verifica se está em posição válida, pois bonecos mortos vão em casas negativas

        int dl = 0, dc = 0;
        char d = Character.toUpperCase(direction);
        switch (d) {
            case 'W': dl = -1; break;
            case 'S': dl = 1; break;
            case 'A': dc = -1; break;
            case 'D': dc = 1; break;
            default: return null;
        }

        int nl = l + dl;
        int nc = c + dc;
        if (nl < 0 || nl >= 10 || nc < 0 || nc >= 10) return null;//foi pra fora dos limites

        Cell origin = table[l][c];
        Cell target = table[nl][nc];

        if (origin.getOccupant() == null || origin.getOccupant() != piece) {
            return "NOT_PLACED";//erro a célula de chamada
        }

        if (target.getOccupant() == null) {
            // mover para célula vazia
            origin.clear();
            target.setOccupant(piece, ownerId);
            piece.setPosition(nl, nc);
            return "MOVED";//conseguiu se mover
        }
        else if(target.getOccupant() != null)
            return "OCCUPED";
        else {
            return null;
        }
    }

    public void removeCharacter(Characters ch) {
        if (ch == null) return;
        int linha = ch.getLinha();
        int coluna = ch.getColuna();

        // Verifica se está dentro dos limites
        if (linha >= 0 && linha < 10 && coluna >= 0 && coluna < 10) {
            Cell cell = table[linha][coluna];
            if (cell.getOccupant() == ch) {
                cell.clear(); // Limpa a célula
            }
        }

        // Atualiza posição do personagem
        ch.setPosition(-1, -1);
    }


    // retorna uma cópia do tabuleiro
    public String[][] getSnapshot() {
        String[][] copy = new String[10][10];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                copy[i][j] = table[i][j].displayChar();
        return copy;
    }
}