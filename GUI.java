import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		int nbOfTiles = 3;
		int size = 300;
		
		Grid grid = new Grid(nbOfTiles, size, new State(nbOfTiles));
		
		Group group = new Group(grid.getTiles().toArray(new Node[grid.getTiles().size()]));
		
		primaryStage.setTitle("Taquin");
		Scene scene = new Scene(group, size, size);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
