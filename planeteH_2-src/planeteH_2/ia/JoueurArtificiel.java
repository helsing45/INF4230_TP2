package planeteH_2.ia;

/*
 * Si vous utilisez Java, vous devez modifier ce fichier-ci.
 *
 * Vous pouvez ajouter d'autres classes sous le package planeteH_2.ia.
 *
 * Prénom Nom    (CODE00000001)
 * Prénom Nom    (CODE00000002)
 */

import planeteH_2.Grille;
import planeteH_2.Joueur;
import planeteH_2.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class JoueurArtificiel implements Joueur {

    private final Random random = new Random();

    /**
     * Voici la fonction à modifier.
     * Évidemment, vous pouvez ajouter d'autres fonctions dans JoueurArtificiel.
     * Vous pouvez aussi ajouter d'autres classes, mais elles doivent être
     * ajoutées dans le package planeteH_2.ia.
     * Vous ne pouvez pas modifier les fichiers directement dans planeteH_2., car ils seront écrasés.
     *
     * @param grille Grille reçu (état courrant). Il faut ajouter le prochain coup.
     * @param delais Délais de rélexion en temps réel.
     * @return Retourne le meilleur coup calculé.
     */
    @Override
    public Position getProchainCoup(Grille grille, int delais) {
        GrilleAnalytics analytics = new GrilleAnalytics(grille);
        ArrayList<Position> casesvides = getAllPossiblePosition(grille);
        int choix = random.nextInt(casesvides.size());
        /*Position lastPositionPLayed = MemoryUtils.getLastPositionPlayed();
        if(lastPositionPLayed == null){
            lastPositionPLayed = new Position(0,0);
        }else {
           lastPositionPLayed = GrilleUtils.getEmptyNeighbour(grille, lastPositionPLayed).get(0);
        }
        MemoryUtils.setLastPositionPlayed(lastPositionPLayed);
        */
        //return casesvides.get(choix);
        return getV(grille);
    }

    public Position getV(Grille grille) {
        Position bestChoice = null;
        int bestEvaluation = Integer.MIN_VALUE;
        List<Position> emptyCells = GrilleUtils.getEmptyCell(grille);
        for (Position emptyCell : emptyCells) {
            Grille nextGrille = grille.clone();
            nextGrille.set(emptyCell, getMyId(grille));
            int evaluationCurrent = GrilleAnalytics.getAnalytics(nextGrille).evaluate(getMyId(grille));
            if (bestEvaluation < evaluationCurrent) {
                bestEvaluation = evaluationCurrent;
                bestChoice = emptyCell;
            }
        }
        return bestChoice;

    }

    public int getMyId(Grille grille) {
        return (GrilleUtils.getAllPlayedPosition(grille).size() % 2) + 1;
    }

    public int getOppId(Grille grille) {
        return getMyId(grille) == 1 ? 2 : 1;
    }

    /*private int[] minimax(Grille grille, int depth, int player) {
        // Generate possible next moves in a List of int[2] of {row, col}.
        List<Grille> nextMoves = GrilleUtils.getNextMoves(player, grille);

        // mySeed is maximizing; while oppSeed is minimizing
        int bestScore = (player == getMyId(grille)) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            // Gameover or depth reached, evaluate score
            bestScore = evaluate();
        } else {
            for (int[] move : nextMoves) {
                // Try this move for the current "player"
                cells[move[0]][move[1]].content = player;
                if (player == getMyId(grille)) {  // mySeed (computer) is maximizing player
                    currentScore = minimax(grille, depth - 1, getOppId(grille))[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {  // oppSeed is minimizing player
                    currentScore = minimax(grille, depth - 1, getMyId(grille))[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                // Undo move
                cells[move[0]][move[1]].content = Seed.EMPTY;
            }
        }
        return new int[]{bestScore, bestRow, bestCol};
    }*/


    public ArrayList<Position> getAllPossiblePosition(Grille grille) {
        ArrayList<Position> casesvides = new ArrayList<>();
        int nbcol = grille.getData()[0].length;
        for (int l = 0; l < grille.getData().length; l++) {
            for (int c = 0; c < nbcol; c++) {
                if (grille.getData()[l][c] == 0) {
                    casesvides.add(new Position(l, c));
                }
            }
        }
        return casesvides;
    }

    @Override
    public String getAuteurs() {
        return "Prénom1 Nom1 (CODE00000001)  et  Prénom2 Nom2 (CODE00000002)";
    }


}
