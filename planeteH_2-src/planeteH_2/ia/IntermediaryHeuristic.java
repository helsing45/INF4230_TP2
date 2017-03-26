package planeteH_2.ia;

import planeteH_2.Grille;
import planeteH_2.Position;

import java.util.List;


public class IntermediaryHeuristic extends Heuristic {

    private int COUNT_TO_WIN = 5;

    private NextPositionHandler horizontalLineHandler = new NextPositionHandler() {
        @Override
        public Position getNextPositivePosition(Position currentPosition) {
            return GrilleUtils.getLeftNeighbour(currentPosition);
        }

        @Override
        public Position getNextNegativePosition(Position currentPosition) {
            return GrilleUtils.getRightNeighbour(grille, currentPosition);
        }
    };

    private NextPositionHandler verticalLineHandler = new NextPositionHandler() {
        @Override
        public Position getNextPositivePosition(Position currentPosition) {
            return GrilleUtils.getTopNeighbour(currentPosition);
        }

        @Override
        public Position getNextNegativePosition(Position currentPosition) {
            return GrilleUtils.getBottomNeighbour(grille, currentPosition);
        }
    };

    private NextPositionHandler diagonalTopLeftToBottomRightHandler = new NextPositionHandler() {
        @Override
        public Position getNextPositivePosition(Position currentPosition) {
            return GrilleUtils.getLeftTopNeighbour(currentPosition);
        }

        @Override
        public Position getNextNegativePosition(Position currentPosition) {
            return GrilleUtils.getRightBottomNeighbour(grille, currentPosition);
        }
    };

    private NextPositionHandler diagonalBottomLeftToTopRightHandler = new NextPositionHandler() {
        @Override
        public Position getNextPositivePosition(Position currentPosition) {
            return GrilleUtils.getLeftBottomNeighbour(grille, currentPosition);
        }

        @Override
        public Position getNextNegativePosition(Position currentPosition) {
            return GrilleUtils.getRightTopNeighbour(grille, currentPosition);
        }
    };

    public IntermediaryHeuristic(Grille grille) {
        super(grille);
    }

    @Override
    protected int getScore(int player) {
        int evaluation = 0;
        evaluation += getGroupScore(horizontalGrouping(player), horizontalLineHandler);
        evaluation += getGroupScore(verticalGrouping(player), verticalLineHandler);
        evaluation += getGroupScore(diagonalTopLeftToBottomRightGrouping(player), diagonalTopLeftToBottomRightHandler);
        evaluation += getGroupScore(diagonalBottomLeftToTopRightGrouping(player), diagonalBottomLeftToTopRightHandler);
        evaluation += singleGroup(player).size();

        return evaluation;
    }

    private int getGroupScore(List<List<Position>> groups, NextPositionHandler handler) {
        int groupeEvaluation = 0;
        for (List<Position> group : groups) {
            groupeEvaluation += getPartialScore(group, handler);
        }
        return groupeEvaluation;
    }

    private int getPartialScore(List<Position> group, NextPositionHandler handler) {
        int partialEvaluation = (int) Math.pow(10, group.size() - 1);
        partialEvaluation -= canBeComplete(group, handler) ? 0 : partialEvaluation * .25;
        return partialEvaluation;
    }

    public boolean canBeComplete(List<Position> group, NextPositionHandler handler) {
        return getAvailableSpace(group, handler) >= COUNT_TO_WIN;
    }

    public int getAvailableSpace(List<Position> group, NextPositionHandler handler) {
        Position nextPositivePosition = group.get(0);
        Position nextNegativePosition = group.get(group.size() - 1);
        int missingValue = COUNT_TO_WIN - group.size();
        int count = 0;
        try {
            for (int i = 0; i < missingValue; i++) {
                if (nextPositivePosition != null)
                    nextPositivePosition = handler.getNextPositivePosition(nextPositivePosition);
                if (nextNegativePosition != null)
                    nextNegativePosition = handler.getNextNegativePosition(nextNegativePosition);

                if (nextPositivePosition != null && grille.get(nextPositivePosition) == 0) {
                    count++;
                }

                if (nextNegativePosition != null && grille.get(nextNegativePosition) == 0) {
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public interface NextPositionHandler {
        Position getNextPositivePosition(Position currentPosition);

        Position getNextNegativePosition(Position currentPosition);
    }

}
