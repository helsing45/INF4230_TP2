package planeteH_2.ia;

import planeteH_2.Grille;
import planeteH_2.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j-c9 on 2017-03-12.
 */
public class GrilleUtils {

    public static int getLineCount(Grille grille) {
        return grille.getData().length;
    }

    public static int getColumnCount(Grille grille) {
        return grille.getData()[0].length;
    }

    public static List<Position> getNeighbour(Grille grille, Position position) {
        int line = position.ligne;
        int column = position.colonne;
        List<Position> neighbour = new ArrayList<>();
        Position temp;
        if (line > 0) {
            neighbour.add(new Position(line - 1, column));
        }
        if (line < getLineCount(grille) - 1) {
            neighbour.add(new Position(line + 1, column));
        }
        if (column > 0) {
            neighbour.add(new Position(line, column - 1));
        }
        if (column < getColumnCount(grille) - 1) {
            neighbour.add(new Position(line, column + 1));
        }
        return neighbour;
    }

    public static List<Position> getEmptyNeighbour(Grille grille, Position position) {
        List<Position> neighbour = getNeighbour(grille, position);
        List<Position> neighbourEmpty = new ArrayList<>();
        for (Position p : neighbour) {
            if (grille.get(p) == 0) {
                neighbourEmpty.add(p);
            }
        }
        return neighbourEmpty;
    }
}
