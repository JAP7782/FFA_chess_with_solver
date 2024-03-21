package puzzles.chess.model;

import puzzles.common.solver.Configuration;

import java.util.Collection;

public interface Piece {
    Collection<Configuration> generateMoves();

}
