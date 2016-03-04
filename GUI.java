import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.awt.Point;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Separator;

public class GUI extends Application {
    private static final int SIZE = 600;
    private static Grid grid;
    private static Text movesCounterText;
    private static ToolBar toolBar;
    private final static double MARGIN_RATE = 1.2;
    private final static Map<KeyCode, Direction> directions = new HashMap<KeyCode, Direction>();
    private static int fewestMoves;
    
    static {
        directions.put(KeyCode.UP, Direction.UP);
        directions.put(KeyCode.DOWN, Direction.DOWN);
        directions.put(KeyCode.LEFT, Direction.LEFT);
        directions.put(KeyCode.RIGHT, Direction.RIGHT);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        int nbOfTiles = selectSize();
        
        grid = new Grid(nbOfTiles, SIZE, (new State(nbOfTiles)).shuffle());
        //fewestMoves = grid.fewestMoves();
        fewestMoves = 10;
        primaryStage.setTitle("Taquin Puzzle");
        
        BorderPane pane = new BorderPane();
        movesCounterText = new Text();
        Button solve = new Button("Solve");
        solve.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                grid.solve();
                winnerDialog();
            }
        });
        toolBar = new ToolBar(solve,
                                      new Separator(),
                                      movesCounterText);
        pane.setTop(toolBar);
        pane.setCenter(createGroup(SIZE));
        pane.setBackground(new Background(new BackgroundFill(Color.CORNSILK, 
                                                             CornerRadii.EMPTY, 
                                                             Insets.EMPTY)));
        Scene scene = new Scene(pane, SIZE * MARGIN_RATE, SIZE * MARGIN_RATE);
        setMovesCounterText(0);
        
        moveTileOnKeyPress(scene);
        moveTileOnMousePress(scene);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    private Group createGroup(int size) {
        Group group = new Group();
        for (Tile tile : grid.getTiles()) {
            group.getChildren().add(tile.getSquare());
            group.getChildren().add(tile.getText());
        }
        
        return group;
    }

    private int selectSize() {
        List<Integer> choices = new ArrayList<>();
        choices.add(2);
        choices.add(3);
        choices.add(4);
        choices.add(5);
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(3, choices);
        dialog.setTitle("Taquin Puzzle");
        dialog.setHeaderText("Please select a size (n x n)\ndefault: n = 3");
        dialog.setContentText("n = ");
        Optional<Integer> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get().intValue();
        }
        return 3;
    }

    private void setMovesCounterText(int nbMoves) {
        movesCounterText.setText("Number of moves: " + nbMoves);
    }

    private void moveTileOnKeyPress(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (directions.containsKey(event.getCode())){
                    grid.move(directions.get(event.getCode()));
                    refresh();
                }
            }
        });
    }

    private void moveTileOnMousePress(Scene scene) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                grid.move(new Point((int)Math.round((event.getSceneX() / MARGIN_RATE)), 
                                    (int)Math.round((event.getSceneY() / MARGIN_RATE) - toolBar.getHeight())));
                refresh();
            }
        });
    }
    
    private void openPopup(String text, Scene scene) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(scene.getWindow());
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text(text));
        Scene dialogScene = new Scene(dialogVbox, 150, 150);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void winnerDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setGraphic(new ImageView(this.getClass().getResource("crown.gif").toString()));
        alert.setTitle("Taquin Puzzle");
        alert.setHeaderText("You win !");
        alert.setContentText("You solve the puzzle in " + grid.getMovesCount() +
                             " moves.");
        alert.showAndWait();
        System.exit(0); // Game is finished
    }
    
    private void refresh() {
        setMovesCounterText(grid.getMovesCount());
        if(grid.isFinish()){
            winnerDialog();
        }
    }
}
