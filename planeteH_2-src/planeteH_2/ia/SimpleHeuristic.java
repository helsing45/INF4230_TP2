package planeteH_2.ia;

import planeteH_2.Grille;
import planeteH_2.Position;

import java.util.ArrayList;
import java.util.List;

public class SimpleHeuristic extends Heuristic {

    public SimpleHeuristic(Grille grille) {
        super(grille);
    }

    public List<List<Position>> getGrouping(int player) {
        List<List<Position>> group = new ArrayList<>();
        group.addAll(horizontalGrouping(player));
        group.addAll(verticalGrouping(player));
        group.addAll(diagonalBottomLeftToTopRightGrouping(player));
        group.addAll(diagonalTopLeftToBottomRightGrouping(player));
        group.addAll(singleGroup(player));
        return group;
    }

    protected int getScore(int player) {
        List<List<Position>> grouping = getGrouping(player);
        int evaluation = 0;
        for (List<Position> positions : grouping) {
            evaluation += Math.pow(10, positions.size() - 1);
        }
        return evaluation;
    }
}
