package view;

import javafx.animation.*;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.Random;

import javafx.scene.layout.Pane;

public class StarryGameBackground {

    private static final int WIDTH = 1980;
    private static final int HEIGHT = 1080;
    private static final int STAR_COUNT = 200;

    private static Random random = new Random();

    public static void show(Pane gamePane) {
        Pane backgroundLayer = new Pane();
        backgroundLayer.setPrefSize(WIDTH, HEIGHT);

        Rectangle blackBackground = new Rectangle(WIDTH, HEIGHT, Color.BLACK);
        backgroundLayer.getChildren().add(blackBackground);

        // Add stars
        for (int i = 0; i < STAR_COUNT; i++) {
            Node star = createFancyStar();
            backgroundLayer.getChildren().add(star);
        }

        // Add planets and moon
        addPlanetsAndMoon(backgroundLayer);

        gamePane.getChildren().add(backgroundLayer);
        backgroundLayer.toBack();

        // Astronaut layer
        Pane astronautLayer = new Pane();
        astronautLayer.setPickOnBounds(false);

        Image image = new Image("boardI/astronaut.png");

        ImageView astronaut1 = new ImageView(image);
        astronaut1.setFitWidth(200);
        astronaut1.setPreserveRatio(true);
        astronaut1.setOpacity(0.8);
        astronaut1.setTranslateX(100);
        astronaut1.setTranslateY(100);

        ImageView astronaut2 = new ImageView(image);
        astronaut2.setFitWidth(200);
        astronaut2.setPreserveRatio(true);
        astronaut2.setOpacity(0.8);
        astronaut2.setScaleX(-1);
        astronaut2.setTranslateX(WIDTH - 300);
        astronaut2.setTranslateY(HEIGHT - 300);

        astronautLayer.getChildren().addAll(astronaut1, astronaut2);
        gamePane.getChildren().add(astronautLayer);

        animateAstronauts(astronaut1, astronaut2);
    }

    private static void addPlanetsAndMoon(Pane pane) {
        // Load planet images
        Image saturnImage = new Image("boardI/saturn.png");
        Image mercuryImage = new Image("boardI/mercury.png");
        Image rock = new Image("boardI/rocks.png");

        // Planet 1 (Saturn)
        ImageView planet1 = new ImageView(saturnImage);
        planet1.setFitWidth(200);
        planet1.setFitHeight(200);
        planet1.setTranslateX(200);  // adjust to align center of image
        planet1.setTranslateY(700);
        planet1.setEffect(new DropShadow(60, Color.web("#304ffe", 0.5)));

        // Planet 2 (Mercury)
        ImageView planet2 = new ImageView(mercuryImage);
        planet2.setFitWidth(140);
        planet2.setFitHeight(140);
        planet2.setTranslateX(1530);
        planet2.setTranslateY(130);
        planet2.setEffect(new DropShadow(50, Color.web("#ff1744", 0.5)));

        // rocks (keeping as a circle)
        ImageView rocks = new ImageView(rock);
        rocks.setFitWidth(800);
        rocks.setFitHeight(700);
        rocks.setTranslateX(0);
        rocks.setTranslateY(0);
        rocks.setEffect(new DropShadow(0.1, Color.WHITE));
       

        pane.getChildren().addAll(planet1, planet2, rocks);
    }

    private static void animateAstronauts(ImageView a1, ImageView a2) {
    	double speed = 0.5;
        double[] velocity1 = {speed, speed};   // Speed of astronaut 1
        double[] velocity2 = {speed, speed}; // Speed of astronaut 2

        final double screenWidth = 1980;
        final double screenHeight = 1089;

        AnimationTimer timer = new AnimationTimer() {
            private double x1 = a1.getTranslateX();
            private double y1 = a1.getTranslateY();
            private double x2 = a2.getTranslateX();
            private double y2 = a2.getTranslateY();

            @Override
            public void handle(long now) {
                x1 += velocity1[0];
                y1 += velocity1[1];
                x2 += velocity2[0];
                y2 += velocity2[1];

                // Edge collision detection for astronaut 1
                if (x1 < 0 || x1 + a1.getBoundsInParent().getWidth() > screenWidth) {
                    velocity1[0] *= -1;
                }
                if (y1 < 0 || y1 + a1.getBoundsInParent().getHeight() > screenHeight) {
                    velocity1[1] *= -1;
                }

                // Edge collision detection for astronaut 2
                if (x2 < 0 || x2 + a2.getBoundsInParent().getWidth() > screenWidth) {
                    velocity2[0] *= -1;
                }
                if (y2 < 0 || y2 + a2.getBoundsInParent().getHeight() > screenHeight) {
                    velocity2[1] *= -1;
                }

                // Collision detection between astronauts
                Bounds bounds1 = a1.getBoundsInParent();
                Bounds bounds2 = a2.getBoundsInParent();
                if (bounds1.intersects(bounds2)) {
                    velocity1[0] *= -1;
                    velocity1[1] *= -1;
                    velocity2[0] *= -1;
                    velocity2[1] *= -1;

                    // Push apart to prevent sticking
                    x1 += velocity1[0] * 5;
                    y1 += velocity1[1] * 5;
                    x2 += velocity2[0] * 5;
                    y2 += velocity2[1] * 5;
                }

                a1.setTranslateX(x1);
                a1.setTranslateY(y1);
                a2.setTranslateX(x2);
                a2.setTranslateY(y2);
            }
        };
        timer.start();
    }


    private static Node createFancyStar() {
        double x = random.nextInt(WIDTH);
        double y = random.nextInt(HEIGHT);
        double radius = 2 + random.nextDouble() * 2.5;

        Color[] starColors = {
            Color.WHITE, Color.rgb(220, 220, 255), Color.rgb(255, 255, 210)
        };
        Color starColor = starColors[random.nextInt(starColors.length)];

        RadialGradient gradient = new RadialGradient(
            0, 0,
            0.5, 0.5,
            0.5,
            true,
            CycleMethod.NO_CYCLE,
            new Stop(0.0, starColor.brighter()),
            new Stop(0.4, starColor),
            new Stop(1.0, Color.TRANSPARENT)
        );

        Circle star = new Circle(radius);
        star.setFill(gradient);
        star.setTranslateX(x);
        star.setTranslateY(y);

        DropShadow glow = new DropShadow();
        glow.setColor(starColor);
        glow.setRadius(3 + random.nextDouble() * 3);
        glow.setSpread(0.6);
        star.setEffect(glow);

        FadeTransition twinkle = new FadeTransition(Duration.seconds(1 + random.nextDouble() * 1.5), star);
        twinkle.setFromValue(0.05);
        twinkle.setToValue(1.0);
        twinkle.setAutoReverse(true);
        twinkle.setCycleCount(Animation.INDEFINITE);
        twinkle.setDelay(Duration.seconds(random.nextDouble() * 3));
        twinkle.play();

        return star;
    }
}
