package view;

import exception.GameException;

import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.player.Marble;
import model.player.Player;
import javafx.scene.image.Image;

public class PlayGame {
	public static double pauseDuration = 0.2;

	public static void play(Stage primaryStage) {
		
		View.play.setOnKeyPressed(e -> {
			if (e.getCode().toString().equals("ENTER")) {
				if (View.game.getPlayers().get(0).getSelectedCard() == null) {
					ShowMessage.show(primaryStage,
							"You have to choose a card !");
					return;
				}
				try {
					// Human player's turn (first)
				View.game.playPlayerTurn();
			} catch (GameException m) {
				ShowMessage.show(primaryStage, m.getMessage());
			}
			View.game.endPlayerTurn2();

			resetMarbleEffects();

			InitializeIcons.Icons();
			UpdateCards.update(); // First

			PauseTransition delayUpdateCells = new PauseTransition(Duration
					.seconds(pauseDuration));
			delayUpdateCells.setOnFinished(ev -> UpdateCells.update());
			delayUpdateCells.play(); // Then, after pauseDuration sec

				if (View.game.checkWin() != null) {
					WinScene.win(primaryStage,
							Color.valueOf(View.game.checkWin().toString()));
					return;
				}
				showTrapTemporarily();

				// Start AI turns with delay between each
				playAITurnsWithDelay(0, primaryStage); // Start from AI player 1
														// (index 0 for loop)

			}
		});
		View.play.setOnMouseClicked(e -> {

			if (View.game.getPlayers().get(0).getSelectedCard() == null) {
				ShowMessage.show(primaryStage, "You have to choose a card !");
				return;
			}
			try {
				// Human player's turn (first)
				View.game.playPlayerTurn();
			} catch (GameException m) {
				ShowMessage.show(primaryStage, m.getMessage());
			}
			View.game.endPlayerTurn2();

			resetMarbleEffects();

			InitializeIcons.Icons();
			UpdateCards.update(); // First

			PauseTransition delayUpdateCells = new PauseTransition(Duration
					.seconds(pauseDuration));
			delayUpdateCells.setOnFinished(ev -> UpdateCells.update());
			delayUpdateCells.play(); // Then, after pauseDuration sec

				if (View.game.checkWin() != null) {
					WinScene.win(primaryStage,
							Color.valueOf(View.game.checkWin().toString()));
					return;
				}
				showTrapTemporarily();

				// Start AI turns with delay between each
				playAITurnsWithDelay(0, primaryStage); // Start from AI player 1
														// (index 0 for loop)

			});
	}

	public static void resetMarbleEffects() {
		for (Marble x : View.game.getBoard().getActionableMarbles()) {
			x.cir.setEffect(null);
			x.cir.setScaleX(1);
			x.cir.setScaleY(1);
		}
		for (Player p : View.game.getPlayers()) {
			for (Marble m : p.getMarbles()) {
				m.cir.setEffect(null);
				m.cir.setScaleX(1);
				m.cir.setScaleY(1);
			}
		}
	}

	public static void showTrapTemporarily() {
		View.trapCell.getChildren().clear();
		if (View.game.getBoard().isTrap) {
			
			View.trapCell.getChildren().add(View.trapView);
			View.game.getBoard().isTrap = false;

			// Show trap for 4 seconds then clear
			PauseTransition trapDelay = new PauseTransition(Duration.seconds(4));
			trapDelay.setOnFinished(ev -> View.trapCell.getChildren().clear());
			trapDelay.play();

		}
	}

	// Recursive function to play 3 AI turns with delay
	private static void playAITurnsWithDelay(int aiIndex, Stage primaryStage) {
		if (aiIndex >= 3) {
			// All AI turns finished
			if (View.game.canPlayTurn() == false) {
				View.game.endPlayerTurn();
				InitializeIcons.Icons();

				playAITurnsWithDelay(0, primaryStage);
			}
			ActOnCard.selectCard(primaryStage);
			ActOnMarble.selectMarble(primaryStage);
			return;
		}

		PauseTransition pause = new PauseTransition(Duration.seconds(5)); // 5-second
																			// pause
																			// between
																			// turns

		pause.setOnFinished(ev -> {
			if (View.game.canPlayTurn()) {
				try {
					View.game.playPlayerTurn(); // play current AI player's turn
				} catch (Exception e) {
					ShowMessage.show(primaryStage, e.getMessage());
				}
				View.game.endPlayerTurn2();
				resetMarbleEffects();
			} else {
				View.game.endPlayerTurn2();
				resetMarbleEffects();
			}

			InitializeIcons.Icons();
			UpdateCards.update(); // First

			PauseTransition delayUpdateCells = new PauseTransition(Duration
					.seconds(pauseDuration));
			delayUpdateCells.setOnFinished(evv -> UpdateCells.update());
			delayUpdateCells.play(); // Then, after pauseDuration sec

			// winning scene
			if (View.game.checkWin() != null) {
				WinScene.win(primaryStage,
						Color.valueOf(View.game.checkWin().toString()));
				return;
			}

			showTrapTemporarily();

			// Call next AI turn after another delay
			playAITurnsWithDelay(aiIndex + 1, primaryStage);
		});
		pause.play();
	}

}