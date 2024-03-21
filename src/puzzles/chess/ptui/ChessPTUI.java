package puzzles.chess.ptui;

import puzzles.chess.model.ChessConfig;
import puzzles.chess.model.MovesUtil;
import puzzles.common.Coordinates;
import puzzles.common.Observer;
import puzzles.chess.model.ChessModel;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class ChessPTUI implements Observer<ChessModel, String> {
    private ChessModel model;

    public void init(String filename) throws IOException {
        this.model = new ChessModel(filename);
        this.model.addObserver(this);
        displayHelp();
        for(String[] s:model.getCurrentConfig().getBoard()){
            System.out.println(Arrays.toString(s));
        }

    }

    @Override
    public void update(ChessModel model, String data) {
        // for demonstration purposes
        System.out.println(data);
        System.out.println(model);
        System.out.println("config:");

        for(String[] s:model.getCurrentConfig().getBoard()){
            System.out.println(Arrays.toString(s));
        }
    }

    private void displayHelp() {
        System.out.println( "h(int)              -- hint next move" );
        System.out.println( "l(oad) filename     -- load new puzzle file" );
        System.out.println( "s(elect) r c        -- select cell at r, c" );
        System.out.println( "q(uit)              -- quit the game" );
        System.out.println( "r(eset)             -- reset the current game" );
    }

    public void run() {
        Scanner in = new Scanner( System.in );

        ChessConfig init = new ChessConfig(MovesUtil.duplicate(model.getCurrentConfig().getBoard()));
        int state = 0;
        Coordinates piece = null;

        for ( ; ; ) {
            System.out.print( "> " );
            String line = in.nextLine();
            String[] words = line.split( "\\s+" );
            if (words.length > 0) {
                if (words[0].startsWith( "q" )) {
                    break;
                }else if (words[0].startsWith( "h" )) {
                    model.getHint();
                    if(model.getCurrentConfig().isSolution()){
                        System.out.println("done!");
                        break;
                    }
                }else if (words[0].startsWith( "s" )) {
                    if(!model.getCurrentConfig().getBoard()[Integer.parseInt(words[1])][Integer.parseInt(words[2])].equals(".") && state ==0){
                         piece = new Coordinates(words[1],words[2]);
                        state++;
                    }else if(state > 0){
                        Coordinates end = new Coordinates(words[1],words[2]);
                        state = 0;
                        model.getSelect(piece,end);

                    }else{
                    System.out.println("No peice at " + piece);
                }
                }else if (words[0].startsWith( "r" )) {
                    model.reset(init);
                }else if (words[0].startsWith( "l`" )) {
                    model.load(words[1]);
                }else {
                    displayHelp();
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ChessPTUI filename");
        } else {
            try {
                ChessPTUI ptui = new ChessPTUI();
                ptui.init(args[0]);
                ptui.run();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }
}

