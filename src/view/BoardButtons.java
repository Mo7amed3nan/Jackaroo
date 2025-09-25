package view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.player.Marble;

public class BoardButtons {
	public static void play() { // view ---
		
		Image image = new Image("/boardI/play4.png"); // replace with your image path
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100); // Optional: scale image
        imageView.setPreserveRatio(true);
        
		View.play = new Button();
		//View.play.getStyleClass().add("brown-button");
		//View.play.setTextFill(Color.BLACK);
		View.play.setGraphic(imageView);
		View.play.setStyle("-fx-background-color: transparent;");
		View.play.setLayoutX(1050);
		View.play.setLayoutY(855);
		View.play.setPrefSize(100, 10);
		View.play.setOnMouseEntered(e -> {
            imageView.setScaleX(1.2);
            imageView.setScaleY(1.2);
        });

        View.play.setOnMouseExited(e -> {
            imageView.setScaleX(1.0);
            imageView.setScaleY(1.0);
        });
		Pane fixedPositionPane = new Pane();
		fixedPositionPane.setPickOnBounds(false);
		fixedPositionPane.getChildren().add(View.play);
		View.gamePane.getChildren().add(fixedPositionPane);
	}

	public static void deselect(Stage primaryStage) { // view with logic ---
	
		Image image = new Image("/boardI/des.png"); // replace with your image path
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(220); // Optional: scale image
        imageView.setPreserveRatio(true);
		
		// view ---
		View.deselectAll = new Button();
		
		View.deselectAll.setGraphic(imageView);
		View.deselectAll.setStyle("-fx-background-color: transparent;");
		View.deselectAll.setLayoutX(730);
		View.deselectAll.setLayoutY(850);
		View.deselectAll.setPrefSize(100, 10);
		View.deselectAll.setOnMouseEntered(e -> {
            imageView.setScaleX(1.2);
            imageView.setScaleY(1.2);
        });

        View.deselectAll.setOnMouseExited(e -> {
            imageView.setScaleX(1.0);
            imageView.setScaleY(1.0);
        });

		Pane fixedPositionPane2 = new Pane();
		fixedPositionPane2.setPickOnBounds(false);
		fixedPositionPane2.getChildren().add(View.deselectAll);

		View.gamePane.getChildren().add(fixedPositionPane2);
		
		// logic ---
		View.deselectAll.setOnMouseClicked(e -> {
			View.game.deselectAll();
			for (ImageView card : View.cardToCardView) {
				card.setEffect(null);
				card.setScaleX(1);
				card.setScaleY(1);
			}
		
			for (Marble x : View.game.getBoard().getActionableMarbles()) {
				x.cir.setEffect(null);
				x.cir.setScaleX(1);
				x.cir.setScaleY(1);
			}
			ActOnCard.selectCard(primaryStage);
		});
	}
	
	// Class-level reference to the button so toggleMusic() can update it
	private static Button musicButton;

	public static void music() {
		musicButton = new Button("🔊");
		updateMusicButtonStyle(true); 

		musicButton.setOnAction(e -> toggleMusic());

		// Style the button visually
		musicButton.setStyle("-fx-background-color: #2c3e50; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 24px; " +       // Larger emoji/text
                "-fx-background-radius: 30; " +
                "-fx-padding: 10 20 10 20;");    // More internal spacing


		// Create overlay and position it
		AnchorPane buttonOverlay = new AnchorPane(musicButton);
		AnchorPane.setTopAnchor(musicButton, 10.0);
		AnchorPane.setRightAnchor(musicButton, 10.0);

		buttonOverlay.setPickOnBounds(false); // click-through outside button
		buttonOverlay.setPrefSize(View.gamePane.getWidth(), View.gamePane.getHeight());

		View.gamePane.getChildren().add(buttonOverlay);
	}

	public static void toggleMusic() {
		if (View.mediaPlayer != null) {
			if (View.mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
				View.mediaPlayer.pause();
				updateMusicButtonStyle(false);
			} else {
				View.mediaPlayer.play();
				updateMusicButtonStyle(true);
			}
		}
	}

	// Change the button's icon/text based on state
	private static void updateMusicButtonStyle(boolean isPlaying) {
		if (musicButton != null) {
			musicButton.setText(isPlaying ? "🔊" : "🔇");
		}
	}

}