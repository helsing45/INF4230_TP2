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

    public static List<Position> getAllPlayedPosition(Grille grille) {

        List<Position> playedPosition = new ArrayList<>();
        for (int lineIndex = 0; lineIndex < getLineCount(grille); lineIndex++) {
            for (int columnIndex = 0; columnIndex < getColumnCount(grille); columnIndex++) {
                if (grille.get(lineIndex, columnIndex) == 1 || grille.get(lineIndex, columnIndex) == 2) {
                    playedPosition.add(new Position(lineIndex, columnIndex));
                }
            }
        }
        return playedPosition;
    }

    public static List<Position> getNeighbour(Grille grille, Position position) {
        List<Position> neighbour = new ArrayList<>();
        add(neighbour, getTopNeighbour(position));
        add(neighbour, getBottomNeighbour(grille, position));
        add(neighbour, getLeftNeighbour(position));
        add(neighbour, getRightNeighbour(grille, position));
        add(neighbour, getLeftTopNeighbour(position));
        add(neighbour, getLeftBottomNeighbour(grille, position));
        add(neighbour, getRightTopNeighbour(grille, position));
        add(neighbour, getRightBottomNeighbour(grille, position));
        return neighbour;
    }

    private static <T> void add(List<T> ts, T t) {
        if (t != null) {
            ts.add(t);
        }
    }

    public static Position getTopNeighbour(Position position) {
        if (position.ligne > 0) {
            return new Position(position.ligne - 1, position.colonne);
        }
        return null;
    }

    public static Position getBottomNeighbour(Grille grille, Position position) {
        if (position.ligne < getLineCount(grille) - 1) {
            return new Position(position.ligne + 1, position.colonne);
        }
        return null;
    }

    public static Position getLeftNeighbour(Position position) {
        if (position.colonne > 0) {
            return new Position(position.ligne, position.colonne - 1);
        }
        return null;
    }

    public static Position getRightNeighbour(Grille grille, Position position) {
        if (position.colonne < getColumnCount(grille)- 1) {
            return new Position(position.ligne, position.colonne + 1);
        }
        return null;
    }

    public static Position getLeftTopNeighbour(Position position) {
        if (position.ligne > 0 && position.colonne > 0) {
            return new Position(position.ligne - 1, position.colonne - 1);
        }
        return null;
    }

    public static Position getLeftBottomNeighbour(Grille grille, Position position) {
        if (position.ligne < getLineCount(grille) - 1 && position.colonne > 0) {
            return new Position(position.ligne + 1, position.colonne - 1);
        }
        return null;
    }

    public static Position getRightTopNeighbour(Grille grille, Position position) {
        if (position.ligne > 0 && position.colonne < getColumnCount(grille) - 1) {
            return new Position(position.ligne - 1, position.colonne + 1);
        }
        return null;
    }

    public static Position getRightBottomNeighbour(Grille grille, Position position) {
        if (position.ligne < getLineCount(grille) - 1 && position.colonne < getColumnCount(grille) - 1) {
            return new Position(position.ligne + 1, position.colonne + 1);
        }
        return null;
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

    public static boolean isAlone(Grille grille, Position position) {
        List<Position> neighbour = getNeighbour(grille, position);
        for (Position nearPosition : neighbour) {
            if (grille.get(nearPosition) == grille.get(position)) {
                return false;
            }
        }
        return true;
    }

    public static List<Position> getEmptyCell(Grille grille) {
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
}
