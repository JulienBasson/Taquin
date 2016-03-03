import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class GUI extends Application {
	
	private static Grid grid;
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
		int nbOfTiles = 3;
		int size = 600;
		
		grid = new Grid(nbOfTiles, size, new State(nbOfTiles));
		
		Group group = new Group();
		for (Tile tile : grid.getTiles()) {
		    group.getChildren().add(tile.getSquare());
		    group.getChildren().add(tile.getText());
		}
		
		primaryStage.setTitle("Taquin");
		Scene scene = new Scene(group, size * MARGIN_RATE, size * MARGIN_RATE, Color.SEASHELL);
		moveTileOnKeyPress(scene);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	private void moveTileOnKeyPress(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override public void handle(KeyEvent event) {
				if(directions.containsKey(event.getCode()))
					grid.move(directions.get(event.getCode()));
			}
		});
	}

}
