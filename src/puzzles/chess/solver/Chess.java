package puzzles.chess.solver;

import puzzles.chess.model.ChessConfig;
import puzzles.common.solver.Solver;

import java.io.IOException;

public class Chess {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Chess filename");
        }else{
            try{
                ChessConfig start = new ChessConfig(args[0]);
                Solver solve = new Solver(start);
                solve.solve();
            }catch(IOException e){
                System.out.println("IO Error.");
            }

        }
    }
}
