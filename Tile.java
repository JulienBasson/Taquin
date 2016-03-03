import java.awt.Point;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Tile {

	private Rectangle square;
	private String id;
	private int size;
	private static final Duration TRANSLATE_DURATION = Duration.seconds(0.10);
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
		transition = createTranslateTransition();
		setPosition(coord);
	}

	private TranslateTransition createTranslateTransition() {
		final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, square);
		transition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent t) {
				square.setX(coord.getX());
				square.setY(coord.getY());
				square.setTranslateX(0);
				square.setTranslateY(0);
			}
		});
		return transition;
	}

	private void setPosition(Point coord) {
        transition.setToX(coord.x - this.coord.x);
        transition.setToY(coord.y - this.coord.y);
		this.coord = coord;
		transition.playFromStart();
	}
	
	public void move(Direction dir)	{
		switch(dir) {
		case UP:
			setPosition(new Point(coord.x, coord.y - size));
			break;
		case DOWN:
			setPosition(new Point(coord.x, coord.y + size));
			break;
		case LEFT:
			setPosition(new Point(coord.x - size, coord.y));
			break;
		case RIGHT:
			setPosition(new Point(coord.x + size, coord.y));
			break;
		}
	}
	
	

	public Rectangle getSquare() {
		return square;
	}
}
