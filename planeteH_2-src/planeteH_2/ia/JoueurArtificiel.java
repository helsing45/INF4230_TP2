package planeteH_2.ia;

/*
 * Si vous utilisez Java, vous devez modifier ce fichier-ci.
 *
 * Vous pouvez ajouter d'autres classes sous le package planeteH_2.ia.
 *
 * Décary Jean-Christophe    (DECJ20119200)
 */

import planeteH_2.Grille;
import planeteH_2.Joueur;
import planeteH_2.Position;

import java.util.List;


public class JoueurArtificiel implements Joueur {
    private long timeTurnEnd;

    private Heuristic getHeuristic(Grille grille){
        return new IntermediaryHeuristic(grille);
    }

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
        timeTurnEnd = System.currentTimeMillis() + delais;
        // Je ne suis pas sur pourquoi mais si j'utilise le minmaxWithTime limit avec deux IA ils ne font qu'alterner les couleurs,
        // Si je planifie 3 coup d'avance (depth 3) aussi, donc avec deux coup d'avances max, ainsi que la limite de temps il fait des movements qui font du sens.

        return timedMinimax(grille, 2, getMyId(grille), Integer.MIN_VALUE, Integer.MAX_VALUE).getSecond();
    }

    private long timeLeft() {
        return timeTurnEnd - System.currentTimeMillis();
    }


    public int getMyId(Grille grille) {
        return (GrilleUtils.getAllPlayedPosition(grille).size() % 2) + 1;
    }

    public int getOppId(Grille grille) {
        return getMyId(grille) == 1 ? 2 : 1;
    }

    /**
     * Min max avec un niveau limite
     * @param grille  la grille.
     * @param depth la profondeur limite.
     * @param player le joueur qui joue
     * @return le score et la position qui a menez à ce score.
     */
    private Pair<Integer, Position> minimax(Grille grille, int depth, int player) {
        int myId = getMyId(grille);
        int oppId = getOppId(grille);
        List<Position> nextMoves = GrilleUtils.getEmptyCell(grille);

        int bestScore = player == myId ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        Position bestPosition = null;

        if (nextMoves.isEmpty() || depth == 0) {
            bestScore = getHeuristic(grille).evaluate(player);
        } else {
            for (Position nextMove : nextMoves) {
                grille.set(nextMove, player);
                if (player == myId) {
                    currentScore = minimax(grille, depth - 1, oppId).getFirst();
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestPosition = nextMove;
                    }
                } else {
                    currentScore = minimax(grille, depth - 1, myId).getFirst();
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestPosition = nextMove;
                    }
                }
                grille.set(nextMove, 0);
            }
        }
        return new Pair<>(bestScore, bestPosition);
    }

    /**
     * MinMax avec prunding
     * @see #minimax(Grille, int, int, int, int)
     */
    private Pair<Integer, Position> minimax(Grille grille, int depth, int player, int alpha, int beta) {
        int myId = getMyId(grille);
        int oppId = getOppId(grille);
        // Generate possible next moves in a list of int[2] of {row, col}.

        List<Position> nextMoves = GrilleUtils.getEmptyCell(grille);

        // mySeed is maximizing; while oppSeed is minimizing
        int score;
        Position bestPosition = null;

        if (nextMoves.isEmpty() || depth == 0 || timeLeft() <= 0) {
            score = getHeuristic(grille).evaluate(player);
            return new Pair<>(score, bestPosition);
        } else {
            for (Position move : nextMoves) {
                grille.set(move, player);
                if (player == myId) {
                    score = minimax(grille, depth - 1, oppId, alpha, beta).getFirst();
                    if (score > alpha) {
                        alpha = score;
                        bestPosition = move;
                    }
                } else {  // oppSeed is minimizing player
                    score = minimax(grille, depth - 1, myId, alpha, beta).getFirst();
                    if (score < beta) {
                        beta = score;
                        bestPosition = move;
                    }
                }
                // undo move
                grille.set(move, 0);
                // cut-off
                if (alpha >= beta) break;
            }
            return new Pair<>((player == myId) ? alpha : beta, bestPosition);
        }
    }

    private Pair<Integer, Position> timedMinimax(Grille grille, int depth, int player, int alpha, int beta) {
        int myId = getMyId(grille);
        int oppId = getOppId(grille);

        List<Position> nextMoves = GrilleUtils.getEmptyCell(grille);

        int score;
        Position bestPosition = null;

        if (nextMoves.isEmpty() || depth == 0) {
            score = getHeuristic(grille).evaluate(player);
            return new Pair<>(score, bestPosition);
        } else {
            for (Position move : nextMoves) {
                if(timeLeft() <= 0) break;

                grille.set(move, player);
                if (player == myId) {
                    score = timedMinimax(grille, depth - 1, oppId, alpha, beta).getFirst();
                    if (score > alpha) {
                        alpha = score;
                        bestPosition = move;
                    }
                } else {  // oppSeed is minimizing player
                    score = timedMinimax(grille, depth - 1, myId, alpha, beta).getFirst();
                    if (score < beta) {
                        beta = score;
                        bestPosition = move;
                    }
                }
                // undo move
                grille.set(move, 0);
                // cut-off
                if (alpha >= beta) break;
            }
            return new Pair<>((player == myId) ? alpha : beta, bestPosition);
        }
    }

    @Override
    public String getAuteurs() {
        return "Décary Jean-Christophe (DECJ20119200)";
    }


}
