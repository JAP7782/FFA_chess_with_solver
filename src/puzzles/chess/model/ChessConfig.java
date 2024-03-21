package puzzles.chess.model;

import puzzles.common.solver.Configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

// TODO: implement your ChessConfig for the common solver

public class ChessConfig implements Configuration {
    private String[][] board;
    private final int rows;
    private final int cols;


    public ChessConfig(String filename) throws IOException {
        File file = new File(filename);
        BufferedReader b = new BufferedReader(new FileReader(file));

        String[] size = b.readLine().split(" ");
        this.rows = Integer.parseInt(size[0]);
        this.cols = Integer.parseInt(size[1]);
        this.board = new String[rows][cols];

        for(int r = 0; r < rows; r++){
            String[] row = b.readLine().split(" ");
            //System.out.println(Arrays.toString(row));
            for(int c = 0; c < cols; c++){
                board[r][c] = row[c];
            }
        }
        //System.out.println(Arrays.deepToString(board));
    }

    public ChessConfig(String[][] board){
        this.board = board;
        this.rows = board.length;
        this.cols = board[0].length;
        /**
        System.out.println("config:");

        for(String[] s:board){
            System.out.println(Arrays.toString(s));
        }
         */

    }

    public String[][] getBoard() {
        return board;
    }

    @Override
    public int hashCode(){
        return Arrays.deepHashCode(board);
    }


    @Override
    public boolean isSolution() {
        int tot = 0;
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                if(!board[r][c].equals(".")){
                    tot++;
                }
            }
        }
        return tot == 1;
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> configlist = new ArrayList<>();
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                if(!board[r][c].equals(".")){
                 if(board[r][c].equals("B")){
                     //System.out.println("found Bishop!");
                    configlist.addAll(MovesUtil.generateMoves(r,c,board,"B"));
                 }else if(board[r][c].equals("K")){
                     //System.out.println("found king!");
                     configlist.addAll(MovesUtil.generateMoves(r,c,board,"K"));
                 }else if(board[r][c].equals("P")){
                     //System.out.println("found pawn!");
                     configlist.addAll(MovesUtil.generateMoves(r,c,board,"P"));
                 }else if(board[r][c].equals("N")){
                     //System.out.println("found knight!");
                     configlist.addAll(MovesUtil.generateMoves(r,c,board,"N"));
                 }else if(board[r][c].equals("R")){
                     //System.out.println("found rook!");
                     configlist.addAll(MovesUtil.generateMoves(r,c,board,"R"));
                 }
                }
            }
        }
        return configlist;

    }
}
