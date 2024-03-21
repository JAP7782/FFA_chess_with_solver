package puzzles.chess.model;

import puzzles.common.solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;

public class MovesUtil {
    public static Collection<Configuration> generateMoves(int row, int col, String[][] board, String piece){
        int maxcol = board[0].length;
        int maxrow = board.length;
        ArrayList<Configuration> moves = new ArrayList<>();

        switch(piece){
            /**
             * for all Knights
             */
            case "N":
                //L to the right
                if(col+2 < maxcol){
                    if(row+1 < maxrow){
                        if(!board[row+1][col+2].equals(".")){
                            String[][] newb1 = duplicate(board);
                            newb1[row][col] = ".";
                            newb1[row+1][col+2] = "N";
                            ChessConfig c = new ChessConfig(newb1);
                            moves.add(c);

                        }
                    }
                    if(row-1 >= 0){
                        if(!board[row-1][col+2].equals(".")){
                            String[][] newb2 = duplicate(board);
                            newb2[row][col] = ".";
                            newb2[row-1][col+2] = "N";
                            ChessConfig c = new ChessConfig(newb2);
                            moves.add(c);
                        }
                    }
                }

                //L to the left
                if(col-2 >= 0){
                    if(row+1 < maxrow){
                        if(!board[row+1][col-2].equals(".")){
                            String[][] newb3 = duplicate(board);
                            newb3[row][col] = ".";
                            newb3[row+1][col-2] = "N";
                            ChessConfig c = new ChessConfig(newb3);
                            moves.add(c);
                        }
                    }
                    if(row-1 >= 0){
                        if(!board[row-1][col-2].equals(".")){
                            String[][] newb4 = duplicate(board);
                            newb4[row][col] = ".";
                            newb4[row-1][col-2] = "N";
                            ChessConfig c = new ChessConfig(newb4);
                            moves.add(c);
                        }
                    }
                }


                //L up
                if(row+2 < maxrow){
                    if(col+1 < maxcol){
                        if(!board[row+2][col+1].equals(".")){
                            String[][] newb5 = duplicate(board);
                            newb5[row][col] = ".";
                            newb5[row+2][col+1] = "N";
                            ChessConfig c = new ChessConfig(newb5);
                            moves.add(c);
                        }
                    }
                    if(col-1 >= 0){
                        if(!board[row+2][col-1].equals(".")){
                            String[][] newb6 = duplicate(board);
                            newb6[row][col] = ".";
                            newb6[row+2][col-1] = "N";
                            ChessConfig c = new ChessConfig(newb6);
                            moves.add(c);
                        }
                    }
                }

                //L down
                if(row-2 >= 0){
                    if(col+1 < maxcol){
                        if(!board[row-2][col+1].equals(".")){
                            String[][] newb7 = duplicate(board);
                            newb7[row][col] = ".";
                            newb7[row-2][col+1] = "N";
                            ChessConfig c = new ChessConfig(newb7);
                            moves.add(c);
                        }
                    }
                    if(col-1 >= 0){
                        if(!board[row-2][col-1].equals(".")){
                            String[][] newb8 = duplicate(board);
                            newb8[row][col] = ".";
                            newb8[row-2][col-1] = "N";
                            ChessConfig c = new ChessConfig(newb8);
                            moves.add(c);
                        }
                    }
                }
                break;

            /**
             * for all Pawns
             */
            case "P":
                if(row+1 < maxrow){
                    //diag right
                    if(col+1 < maxcol && !board[row+1][col+1].equals(".")){
                        String[][] newbR =  duplicate(board);
                        newbR[row+1][col+1] = "P";
                        newbR[row][col] = ".";
                        Configuration newbconfig = new ChessConfig(newbR);
                        moves.add(newbconfig);
                    //diag left
                    }if(col-1 >= 0 && !board[row+1][col-1].equals(".")){
                        String[][] newbL =  duplicate(board);
                        newbL[row+1][col-1] = "P";
                        newbL[row][col] = ".";
                        Configuration newbconfig = new ChessConfig(newbL);
                        moves.add(newbconfig);
                    }
                }
                break;

            /**
             * for all Kings
             */
            case "K":
                for(int r = row-1; r <= row+1;r++){
                    for(int c = col-1; c <= col+1;c++){
                        if(!(r == row && c == col) && r >= 0 && r < maxrow && c >= 0 && c < maxcol){
                            if(!board[r][c].equals(".")){
                                String[][] newb = duplicate(board);
                                newb[row][col] = ".";
                                newb[r][c] = "K";
                                Configuration newbconfig = new ChessConfig(newb);
                                moves.add(newbconfig);
                            }
                        }

                    }
                }
                break;
            /**
             * for all Rooks
              */
            case "R":
                //moving right
                for(int c = 1; c+col < maxcol;c++){
                    int cur = c + col;
                    if(cur < maxcol && !board[row][cur].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[row][cur] = "R";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }
                }
                //moving left
                for(int c = -1; c+col >= 0;c--){
                    int cur = c + col;
                    if(cur >= 0 && !board[row][cur].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[row][cur] = "R";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }

                }
                //moving up
                for(int r = 1; r+row < maxrow;r++){
                    int cur = r + row;
                    if(cur < maxrow && !board[cur][col].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[cur][col] = "R";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }
                }
                //moving down
                for(int r = -1; r+row >= 0;r--){
                    int cur = r + row;
                    if(cur >= 0 && !board[cur][col].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[cur][col] = "R";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }
                }
                break;
                /**
                 * for all bishops
                 */
            case "B":
                //diag NE
                for(int c = 1; c+col < maxcol && c+row < maxrow;c++){
                    if(!board[row+c][col+c].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[row+c][col+c] = "B";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }
                }

                //diag SW
                for(int c = -1; c+col >= 0 && c+row >= 0;c--){
                    if(!board[row+c][col+c].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[row+c][col+c] = "B";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }
                }

                //diag SE
                for(int c = 1; c+col < maxcol && row-c >= 0;c++){
                    if(!board[row-c][col+c].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[row-c][col+c] = "B";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }
                }

                //diag NW
                for(int c = 1; col-c >= 0 && row+c < maxrow;c++){
                    if(!board[row+c][col-c].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[row+c][col-c] = "B";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }
                }
                break;
                /**
                 * for all Queens
                 */
            case "Q":
                //diag NE
                for(int c = 1; c+col < maxcol && c+row < maxrow;c++){
                    if(!board[row+c][col+c].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[row+c][col+c] = "Q";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }
                }

                //diag SW
                for(int c = -1; c+col >= 0 && c+row >= 0;c--){
                    if(!board[row+c][col+c].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[row+c][col+c] = "Q";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }
                }

                //diag SE
                for(int c = 1; c+col < maxcol && row-c >= 0;c++){
                    if(!board[row-c][col+c].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[row-c][col+c] = "Q";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }
                }

                //diag NW
                for(int c = 1; col-c >= 0 && row+c < maxrow;c++){
                    if(!board[row+c][col-c].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[row+c][col-c] = "Q";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }
                }

                //moving right
                for(int c = 1; c+col < maxcol;c++){
                    int cur = c + col;
                    if(cur < maxcol && !board[row][cur].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[row][cur] = "Q";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }
                }
                //moving left
                for(int c = -1; c+col >= 0;c--){
                    int cur = c + col;
                    if(cur >= 0 && !board[row][cur].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[row][cur] = "Q";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }

                }
                //moving up
                for(int r = 1; r+row < maxrow;r++){
                    int cur = r + row;
                    if(cur < maxrow && !board[cur][col].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[cur][col] = "Q";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }
                }
                //moving down
                for(int r = -1; r+row >= 0;r--){
                    int cur = r + row;
                    if(cur >= 0 && !board[cur][col].equals(".")){
                        String[][] newb = duplicate(board);
                        newb[row][col] = ".";
                        newb[cur][col] = "Q";
                        Configuration newbconfig = new ChessConfig(newb);
                        moves.add(newbconfig);
                        break;
                    }
                }
                break;

        }
    return moves;
    }

    public static String[][] duplicate(String[][] a){
        String[][] newBoard = new String[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                newBoard[i][j] = a[i][j];
            }
        }
        return newBoard;
    }

}
