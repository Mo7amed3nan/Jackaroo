package view;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ShowMessage {

    public static void show(Stage owner, String message) {
        String imagePath = "/boardI/error.png";
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(owner);
        popupStage.initStyle(StageStyle.TRANSPARENT);

        // Error icon
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        // Error message label
        Label messageLabel = new Label(message);
        messageLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        messageLabel.setTextFill(Color.web("#b00020")); // Dark red
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(300);

        // Layout: Image + Message
        HBox content = new HBox(15, imageView, messageLabel);
        content.setAlignment(Pos.CENTER_LEFT);

        // OK Button
        Button okButton = new Button("OK");
        okButton.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        okButton.setStyle(
            "-fx-background-color: #b00020; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 10; " +
            "-fx-cursor: hand;"
        );
        okButton.setOnMouseEntered(e -> okButton.setStyle(
            "-fx-background-color: #c62828; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 10;"
        ));
        okButton.setOnMouseExited(e -> okButton.setStyle(
            "-fx-background-color: #b00020; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 10;"
        ));
        okButton.setOnAction(e -> popupStage.close());
        okButton.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                popupStage.close();
            }
        });

        // Main layout
        VBox layout = new VBox(20, content, okButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));
        layout.setStyle(
            "-fx-background-color: rgba(255, 235, 238, 0.97); " + // Light red-pink background
            "-fx-background-radius: 20;"
        );
        layout.setEffect(new DropShadow(20, Color.rgb(200, 0, 0, 0.3)));

        // Scene setup
        Scene scene = new Scene(layout);
        scene.setFill(Color.TRANSPARENT);

        popupStage.setScene(scene);
        popupStage.sizeToScene();

        // Animations
        layout.setOpacity(0);
        layout.setScaleX(0.85);
        layout.setScaleY(0.85);

        FadeTransition fade = new FadeTransition(Duration.millis(300), layout);
        fade.setFromValue(0);
        fade.setToValue(1);

        ScaleTransition scale = new ScaleTransition(Duration.millis(300), layout);
        scale.setFromX(0.85);
        scale.setFromY(0.85);
        scale.setToX(1.0);
        scale.setToY(1.0);

        popupStage.setOnShown(e -> {
            fade.play();
            scale.play();
        });

        popupStage.show();
    }
}
