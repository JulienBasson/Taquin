import java.awt.Point;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class Tile {

	private Rectangle square;
	private Text id;
	private int size;
	private static final Duration TRANSLATE_DURATION = Duration.seconds(0.10);
	private final TranslateTransition transitionSquare;
	private final TranslateTransition transitionId;
	private Point coord;
	
	public Tile(String id, int size, Point coord){
		this.coord = coord;
		this.size = size;
		square = new Rectangle(size, size);
		square.setArcHeight(25);
		square.setArcWidth(25);
		square.setOpacity(0.8);
		square.setFill(Color.SEAGREEN);
        square.setStroke(Color.WHITE);
		this.id = new Text(id);
		this.id.setFont(new Font(size/2));
		this.id.setX(coord.x + size/(this.id.getText().length() > 1 ? 5 : 3));
		this.id.setY(coord.y + size/1.5);
		this.id.setFill(Color.CORNSILK);
		transitionSquare = createTranslateTransitionSquare();
		transitionId = createTranslateTransitionId();
		setPosition(coord);
	}

	private void setPosition(Point coord) {
        transitionSquare.setToX(coord.x - this.coord.x);
        transitionSquare.setToY(coord.y - this.coord.y);
        transitionId.setToX(coord.x - this.coord.x);
        transitionId.setToY(coord.y - this.coord.y);
		this.coord = coord;
		transitionSquare.playFromStart();
		transitionId.playFromStart();
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
	
	public Text getText() {
		return id;
	}
	
	private TranslateTransition createTranslateTransitionSquare() {
		final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, square);
		transition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent t) {
				square.setX(coord.x);
				square.setY(coord.y);
				square.setTranslateX(0);
				square.setTranslateY(0);
			}
		});
		return transition;
	}
	
	private TranslateTransition createTranslateTransitionId() {
		final TranslateTransition transition = new TranslateTransition(TRANSLATE_DURATION, id);
		transition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent t) {
				id.setX(coord.x + size/(id.getText().length() > 1 ? 5 : 3));
				id.setY(coord.y + size/1.5);
				id.setTranslateX(0);
				id.setTranslateY(0);
			}
		});
		return transition;
	}
}
