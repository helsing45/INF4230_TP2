package planeteH_2.ia;

import planeteH_2.Grille;
import planeteH_2.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j-c9 on 2017-03-18.
 */
public class GrilleAnalytics {
    private List<Position> player1PlayedPositions, player2PlayedPositions;
    private Grille grille;

    public static GrilleAnalytics getAnalytics(Grille grille){
        return new GrilleAnalytics(grille);
    }

    public GrilleAnalytics(Grille grille) {
        this.grille = grille;
        player1PlayedPositions = new ArrayList<>();
        player2PlayedPositions = new ArrayList<>();

        for (Position position : GrilleUtils.getAllPlayedPosition(grille)) {
            if (grille.get(position) == 1) {
                player1PlayedPositions.add(position);
            } else {
                player2PlayedPositions.add(position);
            }
        }
        getGrouping(1);
    }

    public int evaluate(int player){
        int player1Score = getScore(1);
        int player2Score = getScore(2);
        player1Score *= player == 1 ? 1 : -1;
        player2Score *= player == 2 ? 1 : -1;
        return player1Score + player2Score;
    }

    private int[] getSeries(int player){
        int[] series = new int[]{0,0,0,0,0};
        List<List<Position>> grouping = getGrouping(player);
        for (List<Position> positions : grouping) {
            series[positions.size() - 1] ++;
        }
        return series;
    }

    private int getScore(int player) {
        List<List<Position>> grouping = getGrouping(player);
        int evaluation = 0;
        for (List<Position> positions : grouping) {
            evaluation += Math.pow(10, positions.size() - 1);
        }
        return evaluation;
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
                if (GrilleUtils.getColumnCount(grille) - 1 == column && group.size() > 1) {
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
                if (GrilleUtils.getLineCount(grille) - 1 == line && group.size() > 1) {
                    groups.add(group);
                }
            }
        }
        return groups;
    }

    public List<List<Position>> diagonalTopLeftToBottomRightGrouping(int player) {
        List<List<Position>> groups = new ArrayList<>();
        List<Position> group = new ArrayList<>();
        /*for (int i = 0; i < GrilleUtils.getLineCount(grille); i++) {
            for (int j = 0; j < GrilleUtils.getColumnCount(grille); j++) {
                System.out.print(grille.getData()[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("============");*/
        for (int c = -grille.getData().length; c < grille.getData()[0].length; c++) {
            int c2 = c;
            int l = 0;
            if (c2 < 0) {
                l = -c2;
                c2 = 0;
            }
            for (; c2 < grille.getData()[0].length && l < grille.getData().length; c2++, l++) {
                if (grille.get(l, c2) == player) {
                    group.add(new Position(l, c2));
                } else {
                    if (group.size() > 1) {
                        groups.add(new ArrayList<>(group));
                    }
                    group.clear();
                }
                //System.out.print(grille.getData()[l][c2] + " ");
            }
            if (group.size() > 1) {
                groups.add(new ArrayList<>(group));
                group.clear();
            }
            //System.out.println();
        }

        return groups;
    }

    public List<List<Position>> diagonalBottomLeftToTopRightGrouping(int player) {
        List<List<Position>> groups = new ArrayList<>();
        List<Position> group = new ArrayList<>();

        /*for (int i = 0; i < GrilleUtils.getLineCount(grille); i++) {
            for (int j = 0; j < GrilleUtils.getColumnCount(grille); j++) {
                System.out.print(grille.getData()[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("============");*/
        for (int c = -grille.getData().length; c < grille.getData()[0].length; c++) {
            int c2 = c;
            int l = grille.getData().length - 1;
            if (c2 < 0) {
                l += c2;
                c2 = 0;
            }
            for (; c2 < grille.getData()[0].length && l >= 0; c2++, l--) {
                //System.out.print(grille.getData()[l][c2] + " ");
                if (grille.get(l, c2) == player) {
                    group.add(new Position(l, c2));
                } else {
                    if (group.size() > 1) {
                        groups.add(new ArrayList<>(group));
                    }
                    group.clear();
                }
            }
            if (group.size() > 1) {
                groups.add(new ArrayList<>(group));
                group.clear();
            }
            //System.out.println();
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
