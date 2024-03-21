package puzzles.chess.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.chess.model.ChessConfig;
import puzzles.chess.model.MovesUtil;
import puzzles.common.Coordinates;
import puzzles.common.Observer;
import puzzles.chess.model.ChessModel;
import puzzles.hoppers.model.HoppersModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ChessGUI extends Application implements Observer<ChessModel, String> {
    private ChessModel model;

    /** The size of all icons, in square dimension */
    private final static int ICON_SIZE = 75;
    /** the font size for labels and buttons */
    private final static int FONT_SIZE = 12;

    private Stage stage;

    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    private ChessConfig initconfig;
    private String initfile;

    // for demonstration purposes
    private Image bishop = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"B.png"));
    private ImageView[][] board;
    private Label GS;
    private BorderPane b;
    private GridPane gridboard;

    /** a definition of light and dark and for the button backgrounds */
    private static final Background LIGHT =
            new Background( new BackgroundFill(Color.WHITE, null, null));
    private static final Background DARK =
            new Background( new BackgroundFill(Color.MIDNIGHTBLUE, null, null));

    @Override
    public void init() {
        // get the file name from the command line
        String filename = getParameters().getRaw().get(0);
        try{
            model = new ChessModel(filename);
        }catch(IOException e){}
        initconfig = new ChessConfig(model.getCurrentConfig().getBoard());
        initfile = new String(filename);
        board = new ImageView[model.getCurrentConfig().getBoard().length][model.getCurrentConfig().getBoard()[0].length];
        model.addObserver(this);
    }

    @Override
    public void start(Stage stage) throws Exception {

        String[][] strboard = MovesUtil.duplicate(model.getCurrentConfig().getBoard());
        //int row = strboard.length;
        //int col = strboard[0].length;

        this.stage = stage;
        b = new BorderPane();
        gridboard = new GridPane();

        int tracker = 0;
        for (int row = 0; row < strboard.length; row++) {
            if((strboard[0].length % 2 == 0)){
                tracker--;
            }
            for (int col = 0; col < strboard[row].length; col++) {
                Button button = new Button();

                button.setMinSize(ICON_SIZE, ICON_SIZE);
                button.setMaxSize(ICON_SIZE, ICON_SIZE);
                if(tracker % 2 == 0){
                    button.setBackground(DARK);
                }else{
                    button.setBackground(LIGHT);
                }

                //button.setOnAction( event -> model.getSelect(new Coordinates(row,col)));

                String piece = strboard[row][col];
                if (!piece.equals(".")) {
                    Image img = new Image(getClass().getResourceAsStream(RESOURCES_DIR+ piece +".png"));
                    this.board[row][col] = new ImageView(img);
                    button.setGraphic(this.board[row][col]);
                }else{
                    WritableImage blank = new WritableImage(1,1);
                    this.board[row][col] = new ImageView(blank);
                    button.setGraphic(this.board[row][col]);
                }
                tracker++;
                gridboard.add(button, col, row);
            }
        }
        b.setCenter(gridboard);
        gridboard.setAlignment(Pos.CENTER);

        FileChooser chooser = new FileChooser();
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        currentPath += File.separator + "data" + File.separator + "chess";  // or "hoppers"
        chooser.setInitialDirectory(new File(currentPath));

        Button load = new Button("Load");
        load.setOnAction( event -> model.load(chooser.showOpenDialog(stage).getPath()));
        Button reset = new Button("Reset");
        reset.setOnAction( event -> model.reset(initconfig));
        Button hint = new Button("Hint");
        hint.setOnAction( event -> model.getHint());

        HBox hbox = new HBox(load, reset, hint);

        GS = new Label("Lets play some chess!!");

        b.setBottom(hbox);
        hbox.setAlignment(Pos.CENTER);

        b.setTop(GS);
        GS.setAlignment(Pos.CENTER);


        Scene scene = new Scene(b);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void update(ChessModel chessModel, String msg) {
        String[][] newstr = chessModel.getCurrentConfig().getBoard();

        if(msg.equals("load")){
            board = new ImageView[model.getCurrentConfig().getBoard().length][model.getCurrentConfig().getBoard()[0].length];
            GridPane newgp = new GridPane();
            b.getChildren().remove(gridboard);
            b.setCenter(newgp);
            newgp.setAlignment(Pos.CENTER);

            int tracker = 0;
            for (int row = 0; row < newstr.length; row++) {
                if((newstr[0].length % 2 == 0)){
                    tracker--;
                }
                for (int col = 0; col < newstr[row].length; col++) {
                    Button button = new Button();

                    button.setMinSize(ICON_SIZE, ICON_SIZE);
                    button.setMaxSize(ICON_SIZE, ICON_SIZE);
                    if(tracker % 2 == 0){
                        button.setBackground(DARK);
                    }else{
                        button.setBackground(LIGHT);
                    }

                    //button.setOnAction( event -> model.getSelect(new Coordinates(row,col)));

                    String piece = newstr[row][col];
                    if (!piece.equals(".")) {
                        Image img = new Image(getClass().getResourceAsStream(RESOURCES_DIR+ piece +".png"));
                        this.board[row][col] = new ImageView(img);
                        button.setGraphic(this.board[row][col]);
                    }else{
                        WritableImage blank = new WritableImage(1,1);
                        this.board[row][col] = new ImageView(blank);
                        button.setGraphic(this.board[row][col]);
                    }
                    tracker++;
                    newgp.add(button, col, row);
                }
            }

        }else if(msg.equals("sel")){

        }else{


            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    String piece = newstr[row][col];
                    if (!piece.equals(".")) {
                        Image img = new Image(getClass().getResourceAsStream(RESOURCES_DIR+ piece +".png"));
                        this.board[row][col].setImage(img);
                    }else{
                        WritableImage blank = new WritableImage(1,1);
                        this.board[row][col].setImage(blank);
                    }
                }
            }

            if(chessModel.getCurrentConfig().isSolution()){
                GS.setText("done!");
            }
        }



        this.stage.sizeToScene();  // when a different sized puzzle is loaded
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
