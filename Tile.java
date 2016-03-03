import java.awt.Point;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Tile {

	private Rectangle square;
	private String id;
	private int size;
	private static final Duration TRANSLATE_DURATION = Duration.seconds(0.25);
	private final TranslateTransition transition;
	private Point coord;
	
	public Tile(String id, int size, Point coord){
		this.id = id;
		this.coord = coord;
		square = new Rectangle(size, size);
		this.size = size;
		square.setArcHeight(15);
		square.setArcWidth(15);
		square.setOpacity(0.5);
		setPosition(coord);
		transition = createTranslateTransition();
		moveSquareOnMousePress();
	}

	private TranslateTransition createTranslateTransition() {
		final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, square);
		transition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent t) {
				square.setX(square.getTranslateX() + square.getX());
				square.setY(square.getTranslateY() + square.getY());
				square.setTranslateX(0);
				square.setTranslateY(0);
			}
		});
		return transition;
	}
	
	private void moveSquareOnMousePress() {
		square.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent event) {
				transition.setToX(event.getSceneX() - square.getX());
				transition.setToY(event.getSceneY() - square.getY());
				transition.playFromStart();
			}
		});
	}

	public void setPosition(Point coord){
		square.setX(coord.getX());
		square.setY(coord.getY());
		this.coord = coord;
	}

	public Rectangle getSquare() {
		return square;
	}
}
