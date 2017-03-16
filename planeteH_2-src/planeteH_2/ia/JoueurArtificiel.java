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
        ArrayList<Position> casesvides = getAllPossiblePosition(grille);
        int choix = random.nextInt(casesvides.size());
        Position lastPositionPLayed = MemoryUtils.getLastPositionPlayed();
        if(lastPositionPLayed == null){
            lastPositionPLayed = new Position(0,0);
        }else {
           lastPositionPLayed = GrilleUtils.getEmptyNeighbour(grille, lastPositionPLayed).get(0);
        }
        MemoryUtils.setLastPositionPlayed(lastPositionPLayed);
        return lastPositionPLayed;
    }

    public ArrayList<Position> getAllPossiblePosition(Grille grille) {
        ArrayList<Position> casesvides = new ArrayList<>();
        int nbcol = grille.getData()[0].length;
        for (int l = 0; l < grille.getData().length; l++) {
            for (int c = 0; c < nbcol; c++) {
                if (grille.getData()[l][c] == 0) {
                    casesvides.add(new Position(l,c));
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
