import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.ChoiceDialog;
import java.util.Optional;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class GUI extends Application {
    private static Grid grid;
    private static Text movesCounterText;
    private final static double MARGIN_RATE = 1.2;
    private final static Map<KeyCode, Direction> directions = new HashMap<KeyCode, Direction>();
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
        int size = 600;
        int nbOfTiles = selectSize();

        grid = new Grid(nbOfTiles, size, new State(nbOfTiles));
        primaryStage.setTitle("Taquin Puzzle");
        Scene scene =
            new Scene(createGroup(size), size * MARGIN_RATE, size * MARGIN_RATE, Color.CORNSILK);
        setMovesCounterText(0);
        moveTileOnKeyPress(scene);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private int fewestMoves(int size) {
        State orig = new State(size);
        Algorithm algo = new IterativeDeepeningAStar(orig);
        return algo.solve(orig).size();
    }

    private Group createGroup(int size) {
        movesCounterText = new Text();
        movesCounterText.setFont(new Font(size / 25));
        movesCounterText.setX(1 * MARGIN_RATE);
        movesCounterText.setY(size + movesCounterText.getFont().getSize() * MARGIN_RATE);
        movesCounterText.setFill(Color.SEAGREEN);
        Group group = new Group(movesCounterText);
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

    public static void setMovesCounterText(int nbMoves) {
        movesCounterText.setText("Number of moves: " + nbMoves);
    }

    private void moveTileOnKeyPress(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (directions.containsKey(event.getCode()))
                    grid.move(directions.get(event.getCode()));
            }
        });
    }

    private void moveTileOnMousePress(Scene scene) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // TODO
            }
        });
    }
}
