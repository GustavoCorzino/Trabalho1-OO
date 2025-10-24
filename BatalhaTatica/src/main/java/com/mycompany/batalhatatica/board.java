package com.mycompany.batalhatatica;

public class Board {
    private Cell[][] table;

    public Board() {
        this.table = new Cell[10][10];
        beginGame();
    }

    private void beginGame(){
        for(int i=0; i<10; i++)
            for(int j=0; j<10; j++)
                table[i][j] = new Cell();
    }

    public String getOccupied(int linha, int coluna) {
        return table[linha][coluna].displayChar();
    }

    public void display(int turno){
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
    }

    // Retorno: null = inválido; "PLACED" = peça colocada;
    // "HIT" = atacou e defensor sobreviveu; "KILL:<owner>" = defensor morreu, owner é dono da peça destruída
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
            return "PLACED";
        } else {
            // combate: attacker x defender
            Characters defender = cell.getOccupant();
            String defenderOwner = cell.getOwner();

            int dano = Damage(attacker, defender);
            defender.receiveDamage(dano);

            if (defender.isDead()) {
                cell.clear();
                // opcional: colocar atacante na posição do defensor (se desejado)
                // attacker.setPosition(linha, coluna);
                return "KILL:" + defenderOwner;
            } else {
                return "HIT";
            }
        }
    }

    // Retornos: null = inválido; "NOT_PLACED" = peça não foi posicionada ainda; "MOVED", "HIT", "KILL:<owner>"
    public String move(String ownerId, Characters piece, char direction) {
        int l = piece.getLinha();
        int c = piece.getColuna();
        if (l < 0 || c < 0) return "NOT_PLACED";

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
        if (nl < 0 || nl >= 10 || nc < 0 || nc >= 10) return null;

        Cell origin = table[l][c];
        Cell target = table[nl][nc];

        if (origin.getOccupant() == null || origin.getOccupant() != piece) {
            return "NOT_PLACED";
        }

        if (target.getOccupant() == null) {
            // mover para célula vazia
            origin.clear();
            target.setOccupant(piece, ownerId);
            piece.setPosition(nl, nc);
            return "MOVED";
        } else {
            // combate ao mover para célula ocupada
            Characters defender = target.getOccupant();
            String defenderOwner = target.getOwner();

            int dano = Damage(piece, defender);
            defender.receiveDamage(dano);

            if (defender.isDead()) {
                // remove defensor e move atacante para a célula
                target.clear();
                origin.clear();
                target.setOccupant(piece, ownerId);
                piece.setPosition(nl, nc);
                return "KILL:" + defenderOwner;
            } else {
                return "HIT";
            }
        }
    }

    private int Damage(Characters atk, Characters def) {
        double base = atk.getAtk()*atk.getCrit() - def.getDef()*def.getResist();
        int dano = (int)base;
        if(atk.getCrit()==0.0) 
            dano=(int)atk.getAtk();
        if (dano < 1) 
            dano = 0;
        return dano;
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