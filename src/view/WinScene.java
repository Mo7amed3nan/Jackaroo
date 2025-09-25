package view;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

import model.Colour;
import model.player.Player;

public class WinScene {

    public static void win(Stage primaryStage, Color col) {
        String imagePath = getImagePath(col);
        String winner = getWinningPlayer(col);

        Label winLabel = new Label("🎉 " + winner + " WINS! 🎉");
        winLabel.setStyle("-fx-font-size: 48px; -fx-text-fill: #2e2e2e; -fx-font-weight: bold;");

        ImageView playerImageView = new ImageView();
        try {
            Image playerImage = new Image(imagePath);
            playerImageView.setImage(playerImage);
        } catch (Exception e) {
            System.err.println("Image not found: " + imagePath);
        }
        playerImageView.setFitWidth(200);
        playerImageView.setPreserveRatio(true);

        // Clip the image to a circle
        Circle clip = new Circle(100, 100, 100);
        playerImageView.setClip(clip);

        // Create the border circle WITHOUT fixed centerX and centerY — rely on StackPane centering
        Circle border = new Circle();
        border.setRadius(100); // same radius as clipping circle
        border.setStroke(col);
        border.setStrokeWidth(8);
        border.setFill(Color.TRANSPARENT);
        border.setStrokeType(StrokeType.OUTSIDE);

        playerImageView.setEffect(new DropShadow(15, Color.DARKGRAY));

        // StackPane to overlay border and image, both centered automatically
        StackPane imageContainer = new StackPane(border, playerImageView);
        imageContainer.setPrefSize(200, 200);
        imageContainer.setMaxSize(200, 200);
        imageContainer.setAlignment(Pos.CENTER);

        // Bounce animation on whole container (border + image)
        TranslateTransition bounce = new TranslateTransition(Duration.seconds(1), imageContainer);
        bounce.setByY(-20);
        bounce.setCycleCount(TranslateTransition.INDEFINITE);
        bounce.setAutoReverse(true);
        bounce.play();

        VBox content = new VBox(40, winLabel, imageContainer);
        content.setAlignment(Pos.CENTER);

        Pane partyLayer = new Pane();
        createConfetti(partyLayer, 100, 1920, 1080);

        StackPane root = new StackPane(content, partyLayer);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #fffce6, #ffebee);");
        root.setPadding(new Insets(50));

        Scene winScene = new Scene(root);

        primaryStage.setScene(winScene);
        primaryStage.setTitle("🏆 Game Over");

        // Fullscreen and disable exit hint if you want
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreen(true);

        
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        primaryStage.show();
    }

    private static void createConfetti(Pane layer, int count, double width, double height) {
        Random rand = new Random();
        ParallelTransition fallAnimation = new ParallelTransition();

        for (int i = 0; i < count; i++) {
            Circle confetti = new Circle(5 + rand.nextInt(5));
            confetti.setFill(Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));
            confetti.setTranslateX(rand.nextDouble() * width);
            confetti.setTranslateY(-rand.nextDouble() * height);

            TranslateTransition drop = new TranslateTransition(Duration.seconds(3 + rand.nextDouble() * 2), confetti);
            drop.setFromY(-50);
            drop.setToY(height + 50);
            drop.setCycleCount(TranslateTransition.INDEFINITE);
            drop.setAutoReverse(false);

            fallAnimation.getChildren().add(drop);
            layer.getChildren().add(confetti);
        }

        fallAnimation.play();
    }

    public static String getImagePath(Color col) {
        int i = 1;
        for (Player p : View.game.getPlayers()) {
            if (toFXColor(p.getColour()) == (col)) {
                break;
            }
            i++;
        }
        return "/boardI/icon" + i + ".gif";
    }

    public static String getWinningPlayer(Color col) {
        int i = 1;
        for (Player p : View.game.getPlayers()) {
            if (toFXColor(p.getColour()) == (col)) {
                break;
            }
            i++;
        }
        if (i == 1) return View.myName;
        else if (i == 2) return View.cpu1;
        else if (i == 3) return View.cpu2;
        else return View.cpu3;
    }

    public static Color toFXColor(Colour colour) {
        switch (colour) {
            case RED: return Color.RED;
            case GREEN: return Color.GREEN;
            case YELLOW: return Color.YELLOW;
            case BLUE: return Color.BLUE;
            default: throw new IllegalArgumentException("Unknown colour: " + colour);
        }
    }
}
