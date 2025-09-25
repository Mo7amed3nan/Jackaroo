package view;

import java.util.ArrayList;

import model.card.Card;
import model.card.standard.Standard;
import model.card.wild.Burner;
import model.card.wild.Saver;
import model.player.Player;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class UpdateCards {
	public static ArrayList<Integer> preCardsNumbers;
	public static int preFirePitCardsNumbers;
	public static ArrayList<ImageView> lastInFirePit = new ArrayList<>();

	public static ImageView getImage(Card c) {
		System.out.println("Card type: " + c.getClass().getName());

		if (c instanceof Burner) {
			ImageView iv = new ImageView(new Image("/cards/burner.png"));
			iv.setFitWidth(100);
			iv.setPreserveRatio(true);
			return iv;
		}
		if (c instanceof Saver) {
			ImageView iv = new ImageView(new Image("/cards/saver.png"));
			iv.setFitWidth(100);
			iv.setPreserveRatio(true);
			return iv;
		}
		if (c instanceof Standard) {
			System.out.println(c);
			Standard card = (Standard) c;
			int rank = card.getRank();
			ImageView iv = new ImageView(new Image("/cards/" + rank
					+ ("" + card.getSuit().toString().charAt(0)).toLowerCase()
					+ ".png"));
			iv.setFitWidth(100);
			iv.setPreserveRatio(true);
			return iv;
		}
		
		return null;
	}

	public static void update() {
		View.topPlayer.getChildren().clear();
		View.bottomPlayer.getChildren().clear();
		View.leftPlayer.getChildren().clear();
		View.rightPlayer.getChildren().clear();
		View.cardToCardView.clear();

		ArrayList<HBox> panes = new ArrayList<>();
		panes.add(View.bottomPlayer);
		panes.add(View.leftPlayer);
		panes.add(View.topPlayer);
		panes.add(View.rightPlayer);

		ArrayList<Player> players = View.game.getPlayers();

		for (int i = 0; i < 4; i++) {
			ArrayList<Card> hand = players.get(i).getHand();
			int oldCount = preCardsNumbers.get(i);
			int newCount = hand.size();
			boolean changed = (oldCount != newCount);

			for (int j = 0; j < hand.size(); j++) {
				Card c = hand.get(j);
				ImageView iv;

				if (i == 0) {
					iv = getImage(c);
					View.cardToCardView.add(iv);
				} else {
					iv = new ImageView(new Image("/cards/back.png"));
					iv.setFitWidth(100);
					iv.setPreserveRatio(true);
				}

				if (changed) {
					if(iv != null)
						transition(iv); // Animate all cards, not just last one
				}

				panes.get(i).getChildren().add(iv);
			}

			preCardsNumbers.set(i, newCount);
		}

		// Update FIRE PIT
		int currentFirePitSize = View.game.getFirePit().size();
		if (currentFirePitSize > 0) {
			if (currentFirePitSize != preFirePitCardsNumbers) {
				Card lastCard = View.game.getFirePit().get(
						currentFirePitSize - 1);
				ImageView firePitCardView = getImage(lastCard);
				firePitTransition(firePitCardView);

				// Limit size of lastInFirePit (e.g., keep last 5 cards)
				if (View.lastInFirePit.size() == 8) {
					View.lastInFirePit.remove(0);
				}
				if (firePitCardView != null) {
					firePitTransition(firePitCardView);
				    View.lastInFirePit.add(firePitCardView); 

				}
			}

			View.firePitPane.getChildren().clear();
			View.firePitPane.setAlignment(Pos.CENTER);
			View.firePitPane.getChildren().addAll(View.lastInFirePit);
		}

		preFirePitCardsNumbers = currentFirePitSize;
		
		DeckView.show();

	}

	public static void transition(ImageView iv) {
		iv.setOpacity(0);
		iv.setScaleX(0.7);
		iv.setScaleY(0.7);
		iv.setTranslateY(30); // Rise up from below

		Duration duration = Duration.millis(700); // Slower transition

		FadeTransition fade = new FadeTransition(duration, iv);
		fade.setFromValue(0);
		fade.setToValue(1);

		ScaleTransition scale = new ScaleTransition(duration, iv);
		scale.setFromX(0.7);
		scale.setFromY(0.7);
		scale.setToX(1);
		scale.setToY(1);

		TranslateTransition slide = new TranslateTransition(duration, iv);
		slide.setFromY(30);
		slide.setToY(0);

		ParallelTransition animation = new ParallelTransition(fade, scale,
				slide);
		animation.setInterpolator(Interpolator.EASE_BOTH);
		animation.play();
	}

	public static void firePitTransition(ImageView iv) {
		iv.setOpacity(0);
		iv.setScaleX(1.05);
		iv.setScaleY(1.05);

		// Random slight offset
		double offsetX = Math.random() * 20 - 10; // ±10 px
		double offsetY = Math.random() * 10 - 5; // ±5 px
		iv.setTranslateX(offsetX);
		iv.setTranslateY(-20 + offsetY); // initial rise

		// Random slight rotation
		double angle = Math.random() * 60 - 30; // ±30 degrees
		iv.setRotate(angle);

		Duration duration = Duration.millis(700); // Slower transition

		FadeTransition fade = new FadeTransition(duration, iv);
		fade.setFromValue(0);
		fade.setToValue(1);

		ScaleTransition scale = new ScaleTransition(duration, iv);
		scale.setFromX(1.05);
		scale.setFromY(1.05);
		scale.setToX(1.0);
		scale.setToY(1.0);

		TranslateTransition slide = new TranslateTransition(duration, iv);
		slide.setFromY(-20 + offsetY);
		slide.setToY(offsetY);

		ParallelTransition animation = new ParallelTransition(fade, scale,
				slide);
		animation.setInterpolator(Interpolator.EASE_OUT);
		animation.play();
	}

}
