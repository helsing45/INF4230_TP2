package planeteH_2.ia;

import planeteH_2.Grille;
import planeteH_2.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by j-c9 on 2017-03-18.
 */
public class GrilleAnalytics {
    private List<Position> player1PlayedPositions, player2PlayedPositions;
    private HashMap<Integer, List<List<Position>>> player1PositionGrouping, player2PositionGrouping;
    private int lineCount, columnCount;
    private Grille grille;

    public GrilleAnalytics(Grille grille) {
        this.grille = grille;
        player1PlayedPositions = new ArrayList<>();
        player2PlayedPositions = new ArrayList<>();
        lineCount = GrilleUtils.getLineCount(grille);
        columnCount = GrilleUtils.getColumnCount(grille);

        for (Position position : GrilleUtils.getAllPlayedPosition(grille)) {
            if (grille.get(position) == 1) {
                player1PlayedPositions.add(position);
            } else {
                player2PlayedPositions.add(position);
            }
        }
        grouping(1);
    }

    private List<List<Position>> grouping(int player) {
        List<List<Position>> group = new ArrayList<>();
        group.addAll(singleGroup(player));
        group.addAll(horizontalGrouping(player));
        group.addAll(verticalGrouping(player));
        return group;
    }

    public List<List<Position>> horizontalGrouping(int player) {
        List<List<Position>> groups = new ArrayList<>();
        List<Position> group;
        for (int line = 0; line < GrilleUtils.getLineCount(grille); line++) {
            group = new ArrayList<>();
            for (int column = 0; column < GrilleUtils.getColumnCount(grille); column++) {
                if (grille.get(line, column) == player) {
                    group.add(new Position(line, column));
                } else {
                    if (group.size() > 1) {
                        groups.add(new ArrayList<>(group));
                    }
                    group.clear();
                }
                if (GrilleUtils.getColumnCount(grille) -1 == column && !group.isEmpty()) {
                    groups.add(group);
                }
            }
        }
        return groups;
    }

    public List<List<Position>> verticalGrouping(int player) {
        List<List<Position>> groups = new ArrayList<>();
        List<Position> group;

        for (int column = 0; column < GrilleUtils.getColumnCount(grille); column++) {
            group = new ArrayList<>();
            for (int line = 0; line < GrilleUtils.getLineCount(grille); line++) {
                if (grille.get(line, column) == player) {
                    group.add(new Position(line, column));
                } else {
                    if (group.size() > 1) {
                        groups.add(new ArrayList<>(group));
                    }
                    group.clear();
                }
                if (GrilleUtils.getLineCount(grille) - 1 == line && !group.isEmpty()) {
                    groups.add(group);
                }
            }
        }
        return groups;
    }

    public List<List<Position>> singleGroup(int player) {
        List<Position> playerPosition = player == 1 ? player1PlayedPositions : player2PlayedPositions;
        List<List<Position>> singlePosition = new ArrayList<>();
        for (Position position : playerPosition) {
            if (GrilleUtils.isAlone(grille, position)) {
                List<Position> positions = new ArrayList<>();
                positions.add(position);
                singlePosition.add(positions);
            }
        }
        return singlePosition;
    }
}
