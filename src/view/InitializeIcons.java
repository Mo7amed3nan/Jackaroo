package view;

import javafx.animation.FadeTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class InitializeIcons {
	public static int circleRadius = 75;
	public static Circle avatarCircle1;
	public static Circle avatarCircle2;
	public static Circle avatarCircle3;
	public static Circle avatarCircle4;
	public static Circle glowCircle1 = new Circle();
	public static Circle glowCircle2 = new Circle();
	public static Circle glowCircle3 = new Circle();
	public static Circle glowCircle4 = new Circle();

	public static Circle glowCircle11 = new Circle();
	public static Circle glowCircle22 = new Circle();
	public static Circle glowCircle33 = new Circle();
	public static Circle glowCircle44 = new Circle();
	public static Color nextColor = Color.ANTIQUEWHITE;

	public static void Icons() {
		View.iconsWrapper.getChildren().clear();
		View.iconsWrapper.setPrefSize(0, 0);

		Image avatar1 = new Image("/boardI/icon4.gif");
		Image avatar2 = new Image("/boardI/icon2.gif");
		Image avatar3 = new Image("/boardI/icon3.gif");
		Image avatar4 = new Image("/boardI/icon1.gif");

		// Player 1 ----------
		avatarCircle1 = new Circle(circleRadius);
		avatarCircle1.setFill(new ImagePattern(avatar1));
		avatarCircle1.setStroke(View.colOrder.get(1));
		avatarCircle1.setStrokeWidth(3);
		avatarCircle1.setCenterX(460);
		avatarCircle1.setCenterY(950);

		glowCircle1 = new Circle(circleRadius + 20);
		glowCircle1.setCenterX(460);
		glowCircle1.setCenterY(950);
		glowCircle1.setFill(View.colOrder.get(1));
		glowCircle1.setOpacity(0.4);
		glowCircle1.setEffect(new GaussianBlur(30));
		glowCircle1.setVisible(false);
		View.iconsWrapper.getChildren().add(glowCircle1);

		glowCircle11 = new Circle(circleRadius + 15);
		glowCircle11.setCenterX(460);
		glowCircle11.setCenterY(950);
		glowCircle11.setFill(nextColor);
		glowCircle11.setOpacity(0.4);
		glowCircle11.setEffect(new GaussianBlur(30));
		glowCircle11.setVisible(false);
		View.iconsWrapper.getChildren().add(glowCircle11);

		Text name1 = new Text(View.cpu3);
		name1.setFill(Color.WHITE); // CHANGED
		name1.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
		name1.setY(950 + circleRadius + 25);
		name1.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
			name1.setX(460 - newVal.getWidth() / 2);
		});
		View.iconsWrapper.getChildren().add(name1);

		// Player 2 -----------
		avatarCircle2 = new Circle(circleRadius);
		avatarCircle2.setFill(new ImagePattern(avatar2));
		avatarCircle2.setStroke(View.colOrder.get(2));
		avatarCircle2.setStrokeWidth(3);
		avatarCircle2.setCenterX(460);
		avatarCircle2.setCenterY(130);

		glowCircle2 = new Circle(circleRadius + 20);
		glowCircle2.setCenterX(460);
		glowCircle2.setCenterY(130);
		glowCircle2.setFill(View.colOrder.get(2));
		glowCircle2.setOpacity(0.4);
		glowCircle2.setEffect(new GaussianBlur(30));
		glowCircle2.setVisible(false);
		View.iconsWrapper.getChildren().add(glowCircle2);

		glowCircle22 = new Circle(circleRadius + 15);
		glowCircle22.setCenterX(460);
		glowCircle22.setCenterY(130);
		glowCircle22.setFill(nextColor);
		glowCircle22.setOpacity(0.4);
		glowCircle22.setEffect(new GaussianBlur(30));
		glowCircle22.setVisible(false);
		View.iconsWrapper.getChildren().add(glowCircle22);

		Text name2 = new Text(View.cpu1);
		name2.setFill(Color.WHITE); // CHANGED
		name2.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
		name2.setY(130 + circleRadius + 25);
		name2.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
			name2.setX(460 - newVal.getWidth() / 2);
		});
		View.iconsWrapper.getChildren().add(name2);

		// Player 3 ---------
		avatarCircle3 = new Circle(circleRadius);
		avatarCircle3.setFill(new ImagePattern(avatar3));
		avatarCircle3.setStroke(View.colOrder.get(3));
		avatarCircle3.setStrokeWidth(3);
		avatarCircle3.setCenterX(1420);
		avatarCircle3.setCenterY(130);

		glowCircle3 = new Circle(circleRadius + 20);
		glowCircle3.setCenterX(1420);
		glowCircle3.setCenterY(130);
		glowCircle3.setFill(View.colOrder.get(3));
		glowCircle3.setOpacity(0.4);
		glowCircle3.setEffect(new GaussianBlur(30));
		glowCircle3.setVisible(false);
		View.iconsWrapper.getChildren().add(glowCircle3);

		glowCircle33 = new Circle(circleRadius + 15);
		glowCircle33.setCenterX(1420);
		glowCircle33.setCenterY(130);
		glowCircle33.setFill(nextColor);
		glowCircle33.setOpacity(0.4);
		glowCircle33.setEffect(new GaussianBlur(30));
		glowCircle33.setVisible(false);
		View.iconsWrapper.getChildren().add(glowCircle33);

		Text name3 = new Text(View.cpu2);
		name3.setFill(Color.WHITE); // CHANGED
		name3.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
		name3.setY(130 + circleRadius + 25);
		name3.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
			name3.setX(1420 - newVal.getWidth() / 2);
		});
		View.iconsWrapper.getChildren().add(name3);

		// Player 4 ---------
		avatarCircle4 = new Circle(circleRadius);
		avatarCircle4.setFill(new ImagePattern(avatar4));
		avatarCircle4.setStroke(View.colOrder.get(0));
		avatarCircle4.setStrokeWidth(3);
		avatarCircle4.setCenterX(1420);
		avatarCircle4.setCenterY(950);

		glowCircle4 = new Circle(circleRadius + 20);
		glowCircle4.setCenterX(1420);
		glowCircle4.setCenterY(950);
		glowCircle4.setFill(View.colOrder.get(0));
		glowCircle4.setOpacity(0.4);
		glowCircle4.setEffect(new GaussianBlur(30));
		glowCircle4.setVisible(false);
		View.iconsWrapper.getChildren().add(glowCircle4);

		glowCircle44 = new Circle(circleRadius + 15);
		glowCircle44.setCenterX(1420);
		glowCircle44.setCenterY(950);
		glowCircle44.setFill(nextColor);
		glowCircle44.setOpacity(0.4);
		glowCircle44.setEffect(new GaussianBlur(30));
		glowCircle44.setVisible(false);
		View.iconsWrapper.getChildren().add(glowCircle44);

		Text name4 = new Text(View.myName);
		name4.setFill(Color.WHITE); // CHANGED
		name4.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
		name4.setY(950 + circleRadius + 25);
		name4.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> {
			name4.setX(1420 - newVal.getWidth() / 2);
		});
		View.iconsWrapper.getChildren().add(name4);

		// Hide all
		glowCircle1.setVisible(false);
		glowCircle2.setVisible(false);
		glowCircle3.setVisible(false);
		glowCircle4.setVisible(false);

		glowCircle11.setVisible(false);
		glowCircle22.setVisible(false);
		glowCircle33.setVisible(false);
		glowCircle44.setVisible(false);

		Circle currentGlow = null;
		Circle nextGlow = null;
		int index = View.game.getCurrentPlayerIndex();

		switch (index) {
		case 0:
			currentGlow = glowCircle4;
			nextGlow = glowCircle11;
			break;
		case 1:
			currentGlow = glowCircle1;
			nextGlow = glowCircle22;
			break;
		case 2:
			currentGlow = glowCircle2;
			nextGlow = glowCircle33;
			break;
		case 3:
			currentGlow = glowCircle3;
			nextGlow = glowCircle44;
			break;
		}

		if (currentGlow != null) {
			currentGlow.setVisible(true);
			FadeTransition ft = new FadeTransition(Duration.seconds(1.2), currentGlow);
			ft.setFromValue(0.3);
			ft.setToValue(0.7);
			ft.setAutoReverse(true);
			ft.setCycleCount(FadeTransition.INDEFINITE);
			ft.play();
		}

		if (nextGlow != null) {
			nextGlow.setVisible(true);
			FadeTransition ft = new FadeTransition(Duration.seconds(1.2), nextGlow);
			ft.setFromValue(0.3);
			ft.setToValue(0.7);
			ft.setAutoReverse(true);
			ft.setCycleCount(FadeTransition.INDEFINITE);
			ft.play();
		}

		View.iconsWrapper.getChildren().add(avatarCircle1);
		View.iconsWrapper.getChildren().add(avatarCircle2);
		View.iconsWrapper.getChildren().add(avatarCircle3);
		View.iconsWrapper.getChildren().add(avatarCircle4);
	}
}
