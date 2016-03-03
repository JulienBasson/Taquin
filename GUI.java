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

public class GUI extends Application {
	
	private static Grid grid;
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
		int size = 300;
		
		grid = new Grid(nbOfTiles, size, new State(nbOfTiles));
		
		Group group = new Group(grid.getTiles().toArray(new Node[grid.getTiles().size()]));
		
		primaryStage.setTitle("Taquin");
		Scene scene = new Scene(group, size, size);
		moveTileOnKeyPress(scene);
		primaryStage.setScene(scene);
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
