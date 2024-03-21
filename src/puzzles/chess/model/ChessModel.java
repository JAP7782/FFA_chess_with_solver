package puzzles.chess.model;

import javafx.stage.FileChooser;
import puzzles.common.Coordinates;
import puzzles.common.Observer;
import puzzles.common.solver.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ChessModel {
    /** the collection of observers of this model */
    private final List<Observer<ChessModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private ChessConfig currentConfig;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<ChessModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }

    public ChessConfig getCurrentConfig() {
        return currentConfig;
    }

    //make methods for actions here
    public void getHint(){
        Collection<Configuration> board = currentConfig.getNeighbors();
        for (Configuration config : board) {
            if (config != null) {
                currentConfig = (ChessConfig) config;
                break;
            }
        }
        alertObservers("");

    }

    public void getSelect(Coordinates start,Coordinates end){
        String[][] board = MovesUtil.duplicate(currentConfig.getBoard());
        Collection<Configuration> moves = currentConfig.getNeighbors();
        int confirm = 0;

            board[end.row()][end.col()] = board[start.row()][start.col()];
            board[start.row()][start.col()] = ".";
            Configuration selprod = new ChessConfig(board){
            };
            for(Configuration c:moves){
                if(selprod.hashCode() == c.hashCode()){
                    confirm++;
                }
            }
            if(confirm > 0){
                currentConfig = (ChessConfig) selprod;
                alertObservers("");
            }else{
                System.out.println("Invalid move. Select starting again and choose a valid move.");
            }


    }

    public void reset(ChessConfig init){
        currentConfig = init;
        alertObservers("load");
    }

    public void load(String filename){
        try{
            ChessConfig loaded = new ChessConfig(filename);
            currentConfig = loaded;
            alertObservers("load");
        }catch(IOException e){}



    }


    public ChessModel(String filename) throws IOException {
        FileChooser chooser = new FileChooser();
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        currentPath += File.separator + "data" + File.separator + "chess";  // or "hoppers"
        chooser.setInitialDirectory(new File(currentPath));
        this.currentConfig = new ChessConfig(filename);


    }
}
