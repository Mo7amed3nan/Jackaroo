package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.card.Card;
import model.card.standard.Seven;
import model.card.standard.Standard;
import model.player.Marble;
import model.player.Player;

public class ActOnCard {
	public static int idx = -1;

	public static void selectCard(Stage primaryStage) {

		double scale = 1.1;

		Player currPlayer = View.game.getPlayers().get(0);
		int handSize = currPlayer.getHand().size();
		int viewSize = View.cardToCardView.size();
		int minSize = Math.min(handSize, viewSize);

		for (int i = 0; i < minSize; i++) {
			final int index = i; // must be final for lambda
			ImageView cardView = View.cardToCardView.get(index);

			// Click to select
			cardView.setOnMouseClicked(e -> {
				if (idx != -1) {
					// deselect the previous card
					currPlayer.deselectCard();
					ImageView lastCard = View.cardToCardView.get(idx);
					lastCard.setScaleX(1);lastCard.setScaleY(1);
					lastCard.setEffect(null);

				}
  				if(idx == index){idx = -1; return; }
				idx = index;

				DropShadow glow = new DropShadow();
				glow.setColor(Color.RED.deriveColor(0, 1.0, 1.5, 0.8));
				glow.setRadius(20);
				glow.setSpread(0.65);
				cardView.setEffect(glow);

				try {
					Card selectedCard = currPlayer.getHand().get(index);
					if (selectedCard instanceof Seven) {
						showPopup(primaryStage);
					}

					currPlayer.selectCard(selectedCard);
					System.out.println(selectedCard);
				} catch (Exception ex) {
					ShowMessage.show(primaryStage, ex.getMessage());
				}

			});

			// Hover to show card info
			final Card hoverCard = currPlayer.getHand().get(index);
			final String name = hoverCard.getName();
			final String desc = hoverCard.getDescription();
			final String rank;
			final String suit;

			if (hoverCard instanceof Standard) {
				Standard std = (Standard) hoverCard;
				rank = String.valueOf(std.getRank());
				suit = std.getSuit().toString();
			} else {
				rank = "N/A";
				suit = "N/A";
			}

			cardView.setOnMouseEntered(e -> {
				View.nameL.setText("Name: " + name);
				View.rankL.setText("Rank: " + rank);
				View.suitL.setText("Suit: " + suit);
				View.descL.setText("Description: " + desc);
				cardView.setScaleX(scale);
				cardView.setScaleY(scale);
				View.descBox.setVisible(true);

			});

			cardView.setOnMouseExited(e -> {
				if (idx != index) {
					View.nameL.setText("");
					View.rankL.setText("");
					View.suitL.setText("");
					View.descL.setText("");
					cardView.setScaleX(1);
					cardView.setScaleY(1);
				}
				View.descBox.setVisible(false);
			});
		}

		idx = -1;
	}

	public static void showPopup(Stage owner) { // the split distance for card 7
		// Popup Stage
		View.popupStage = new Stage();
		View.popupStage.initModality(Modality.WINDOW_MODAL); // Modal to the
																// owner
		View.popupStage.initOwner(owner); // Set the owner

		View.popupStage.setTitle("Split Distance");

		Label label = new Label("Select split distance:");

		Slider slider = new Slider(1, 6, 1);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(0);
		slider.setSnapToTicks(true);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);

		Button okButton = new Button("OK");
		okButton.setOnAction(e -> {
			int value = (int) slider.getValue();
			System.out.println("Selected split distance: " + value);
			View.game.getBoard().setSplitDistance(value);
			View.popupStage.close();
		});
		okButton.setOnKeyPressed(e -> {
			if (e.getCode().toString().equals("ENTER")) {
				int value = (int) slider.getValue();
				System.out.println("Selected split distance: " + value);
				View.game.getBoard().setSplitDistance(value);
				View.popupStage.close();
			}
		});
		VBox layout = new VBox(10, label, slider, okButton);
		layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

		Scene popupScene = new Scene(layout, 300, 150);
		View.popupStage.setScene(popupScene);

		// Center on owner
		View.popupStage.setX(owner.getX() + (owner.getWidth() - 300) / 2);
		View.popupStage.setY(owner.getY() + (owner.getHeight() - 150) / 2);

		View.popupStage.showAndWait();
	}
}