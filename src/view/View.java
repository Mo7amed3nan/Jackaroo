package view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import engine.Game;
import model.Colour;
import model.card.Card;
import model.card.standard.Ace;
import model.card.standard.Standard;
import model.card.standard.Suit;
import model.player.Marble;
import model.player.Player;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class View extends Application {
	// have all cells as circles
	public static ArrayList<Circle> cellAccumulator;

	/** cell proberties **/
	public static ArrayList<Color> colOrder;
	public static Color CellColour = Color.BLUE;
	public static int cellR;

	/** maps **/
	public static ArrayList<ArrayList<Pair>> homeindexToPoint;
	public static ArrayList<ArrayList<Pair>> safeZoneIndexToPoint;
	public static ArrayList<Pair> indexToPoint;
	public static HashMap<Pair, Button> pointToButton;
	public static HashMap<Button, Pair> buttonToPoint;
	public static HashMap<Pair, Integer> pointToIndex;

	// media player
	public static MediaPlayer mediaPlayer;

	// map view of card to card
	public static HashMap<Image, Card> cardViewToCard;
	public static ArrayList<ImageView> cardToCardView;
	/** Panes **/
	public static ArrayList<ImageView> lastInFirePit;
	public static ImageView trapView;

	public static StackPane gamePane; // the main root for scene 2
	public static Pair[] homeCellsPos;
	// the cards for the players
	public static StackPane allCards;
	public static ImageView firPitCard;
	public static HBox topPlayer;
	public static HBox bottomPlayer;
	public static HBox leftPlayer;
	public static HBox rightPlayer;
	public static StackPane firePitPane;
	public static Pane iconsWrapper;

	// the first thing to be added : the back ground
	public static ImageView bgV2;

	// the second thing : board image
	public static ImageView boardV;
	public static AnchorPane homeBackGround;
	public static AnchorPane buttonsBackGround;
	// the cells circles to be the third thing after the board
	public static AnchorPane cellsPane;

	// marbles that will be on the cells
	public static AnchorPane marblesPane;

	public static Button play;
	public static AnchorPane playPane;
	public static Button deselectAll;
	/** game **/
	public static Game game;

	// the description for each card
	public static Label rankL;
	public static Label nameL;
	public static Label descL;
	public static Label suitL;
	public static VBox descBox;
	public static AnchorPane linesPane;
	// ////////
	public static Stage popupStage;
	public static StackPane trapCell;
	// //////
	public static final double MARBLE_RADIUS = 25;
	public static final int STAR_COUNT = 200;
	public static final Random random = new Random();
	
	// the deck
	public static AnchorPane deck;
	public static String myName = "";
	public static String cpu1 = "Rudeus";
	public static String cpu2 = "Frieren";
	public static String cpu3 = "Übel";
	public Timeline marbleTimeline;

	@Override
	public void start(Stage primaryStage) {
		showIntroVideo(primaryStage);
		
		primaryStage.setTitle("Jackaroo");
		primaryStage.getIcons().add(new Image("/mainMenu/appIcon.png"));
		
	}

	private void showIntroVideo(Stage primaryStage) {
		
		
		String videoPath = "resources/media/intro.mp4"; // Ensure this exists
		Media media = new Media(new File(videoPath).toURI().toString());
		MediaPlayer introPlayer = new MediaPlayer(media);
		MediaView mediaView = new MediaView(introPlayer);

		// Fit video to full screen
		mediaView.setFitWidth(1980);
		mediaView.setFitHeight(1080);
		mediaView.setPreserveRatio(false);

		// Hint label
		Label skipHint = new Label("Press Enter to skip");
		skipHint.setTextFill(Color.WHITE);
		skipHint.setStyle("-fx-font-size: 24px; -fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 10px;");
		StackPane.setAlignment(skipHint, Pos.BOTTOM_CENTER);
		StackPane.setMargin(skipHint, new Insets(0, 0, 40, 0));

		StackPane videoPane = new StackPane(mediaView, skipHint);
		Scene introScene = new Scene(videoPane, 1980, 1080);

		// Keyboard handler to skip
		introScene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				introPlayer.stop();
				introPlayer.dispose();
				showMainMenu(primaryStage);
			}
		});

		introPlayer.setOnEndOfMedia(() -> {
			introPlayer.dispose();
			showMainMenu(primaryStage);
		});

		primaryStage.setScene(introScene);
		primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitHint("");
		primaryStage.show();

		introPlayer.play();
	}

	private void showMainMenu(Stage primaryStage) {
		
		playMusic();

		Pane marblePane = new Pane();
		marblePane.setStyle("-fx-background-color: black;");

		for (int i = 0; i < STAR_COUNT; i++) {
			Circle star = new Circle(0.5 + random.nextDouble() * 1.5,
					Color.WHITE);
			star.setCenterX(random.nextDouble() * 1920);
			star.setCenterY(random.nextDouble() * 1080);
			marblePane.getChildren().add(star);
		}
//----------------
		Image image = new Image("/boardI/TXT.png"); // Replace with your image path
        ImageView imageView = new ImageView(image);

        // Optional: set size constraints
        imageView.setFitWidth(800);
        imageView.setPreserveRatio(true);
        
        ScaleTransition st = new ScaleTransition(Duration.millis(500), imageView);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToX(1.2);
        st.setToY(1.2);
        st.setAutoReverse(true);
        st.setCycleCount(2); 
        
        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        // Combine into sequential transition
        SequentialTransition sequence = new SequentialTransition(st, pause);
        sequence.setCycleCount(Animation.INDEFINITE);
        sequence.play();


        // Create root pane
        StackPane txt = new StackPane(imageView);        
        txt.setAlignment(Pos.TOP_CENTER);
		txt.setTranslateY(200);
        
       
        
//--------------
		VBox uiLayer = new VBox(20);
		uiLayer.setAlignment(Pos.CENTER);

		TextField nameField = new TextField();
		nameField.setPromptText("Enter your name");
		nameField.setFont(Font.font("Verdana", 20));
		nameField.setMaxWidth(300);
		nameField.setStyle("-fx-background-color: rgba(255,255,255,0.1);"
				+ "-fx-text-fill: white;" + "-fx-prompt-text-fill: gray;"
				+ "-fx-background-radius: 15;" + "-fx-padding: 10;"
				+ "-fx-border-color: white;" + "-fx-border-radius: 15;");

		Button startButton = new Button("Start Game");
		startButton.setFont(Font.font("Arial", 18));
		startButton
				.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-background-radius: 10;");

		uiLayer.getChildren().addAll(nameField,startButton);

		StackPane root = new StackPane(marblePane,txt,uiLayer);
		root.setStyle("-fx-background-color: black;");
		Scene scene = new Scene(root, 1920, 1080);

		marbleTimeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
			Circle marble = create3DMarble(scene);
			marblePane.getChildren().add(marble);
			playTranslateAnimation(marble, marblePane);
		}));
		marbleTimeline.setCycleCount(Animation.INDEFINITE);
		marbleTimeline.play();

		Runnable startGame = () -> {
			myName = nameField.getText().trim();
			if (!myName.isEmpty()) {
				System.out.println("Game would start for: " + myName);
				try {
					gameScene(primaryStage);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				nameField.setPromptText("Name required!");
			}
		};

		startButton.setOnAction(e -> startGame.run());
		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				startGame.run();
			}
		});
	
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.show();
	}

	public Circle create3DMarble(Scene scene) {
		double x = MARBLE_RADIUS + random.nextDouble()
				* (scene.getWidth() - 2 * MARBLE_RADIUS);
		double y = MARBLE_RADIUS + random.nextDouble()
				* (scene.getHeight() - 2 * MARBLE_RADIUS);

		Circle circle = new Circle(x, y, MARBLE_RADIUS);

		Stop[] stops = new Stop[] { new Stop(0, Color.WHITE),
				new Stop(1, randomColor().darker()) };
		RadialGradient gradient = new RadialGradient(0, 0.1, x - MARBLE_RADIUS
				/ 3, y - MARBLE_RADIUS / 3, MARBLE_RADIUS, false,
				CycleMethod.NO_CYCLE, stops);
		circle.setFill(gradient);

		Light.Distant light = new Light.Distant();
		light.setAzimuth(135);
		Lighting lighting = new Lighting(light);
		lighting.setSurfaceScale(3.0);
		circle.setEffect(lighting);

		circle.setOpacity(0);
		return circle;
	}

	public void playTranslateAnimation(Circle marble, Pane parent) {
		FadeTransition fadeIn = new FadeTransition(Duration.millis(500), marble);
		fadeIn.setFromValue(0);
		fadeIn.setToValue(1);

		double dx = random.nextDouble() * 150 - 75;
		double dy = random.nextDouble() * 150 - 75;

		TranslateTransition move = new TranslateTransition(
				Duration.seconds(2 + random.nextDouble()), marble);
		move.setByX(dx);
		move.setByY(dy);

		FadeTransition fadeOut = new FadeTransition(Duration.millis(500),
				marble);
		fadeOut.setFromValue(1);
		fadeOut.setToValue(0);
		fadeOut.setOnFinished(e -> parent.getChildren().remove(marble));

		new SequentialTransition(fadeIn, move, fadeOut).play();
	}

	public Color randomColor() {
		return Color.color(random.nextDouble(), random.nextDouble(),
				random.nextDouble());
	}

	public static boolean end = false;

	public void gameScene(Stage primaryStage) throws Exception {
		cellR = 9;

		// the game
		game = new Game(myName);

		// initializaton

		gamePane = new StackPane();
		cellsPane = new AnchorPane();
		topPlayer = new HBox(20);
		bottomPlayer = new HBox(20);
		rightPlayer = new HBox(20);
		leftPlayer = new HBox(20);
		marblesPane = new AnchorPane();
		firePitPane = new StackPane();
		iconsWrapper = new Pane();
		lastInFirePit = new ArrayList<>();
		 trapView = new ImageView(new Image("/boardI/trap.gif"));
		trapView.setFitWidth(300);
		trapView.setFitHeight(300);

		deck = new AnchorPane();
		homeCellsPos = new Pair[4];
		rankL = new Label();
		nameL = new Label();
		suitL = new Label();
		descL = new Label();
		descBox = new VBox(5);
		descBox.getChildren().addAll(nameL, suitL, rankL, descL);
		trapCell = new StackPane();
		cardToCardView = new ArrayList<>();
		indexToPoint = new ArrayList<>();
		cellAccumulator = new ArrayList<>();
		colOrder = new ArrayList<>();
		pointToButton = new HashMap<>();
		buttonToPoint = new HashMap<>();
		pointToIndex = new HashMap<>();
		safeZoneIndexToPoint = new ArrayList<>();
		homeindexToPoint = new ArrayList<>();
		play = new Button();
		playPane = new AnchorPane();
		deselectAll = new Button();
		firPitCard = new ImageView();
		homeBackGround = new AnchorPane();
		buttonsBackGround = new AnchorPane();
		 linesPane = new AnchorPane();
		// get color order
		for (Player p : game.getPlayers()) {
			Colour col = p.getColour();
			Color color = Color.valueOf(col.toString());
			colOrder.add(color);
		}
		// card description box
		descBox.setPadding(new Insets(10));
		descBox.setStyle("-fx-background-color: #001f3f;"
				+ "-fx-background-radius: 8;" + "-fx-border-color: black;"
				+ "-fx-border-radius: 8;" + "-fx-padding: 8;");
		descBox.setMinWidth(300);
		descBox.setMaxWidth(300);
		descBox.setMaxHeight(200);
		descBox.setMinHeight(200);
		descBox.setVisible(false);

		// make text white and wrap it
		descL.setStyle("-fx-text-fill: white;");
		rankL.setStyle("-fx-text-fill: white;");
		suitL.setStyle("-fx-text-fill: white;");
		nameL.setStyle("-fx-text-fill: white;");

		descL.setWrapText(true);
		rankL.setWrapText(true);
		suitL.setWrapText(true);
		nameL.setWrapText(true);

		StackPane.setAlignment(descBox, Pos.BOTTOM_LEFT);
		StackPane.setMargin(descBox, new Insets(10));

		/** INITIALIZE cells **/
		int startx = 1140;
		int starty = 785;
		int sk = 23;
		int strokeWidth = 3;

		InitializeTrack.initializeTrackCells(startx, starty, sk, cellR);

		/** INITIALIZE for safezone position **/

		InitializeTrack.initializeSafeZone(sk, cellR, strokeWidth);

		/** INITIALIZE homecells positin **/
		InitializeTrack.initializeHomecells(sk, cellR, strokeWidth);

		// if you started before here ... -------><
		// ////////////////////////////////////////////////
		// astronauts 
		StarryGameBackground.show(gamePane);

		
		// first : board and background

				// the background
		Image bg2 = new Image("/boardI/theBoard2.png");
		bgV2 = new ImageView(bg2);
		bgV2.setFitWidth(900);
		bgV2.setPreserveRatio(true);
		gamePane.getChildren().add(bgV2);

		gamePane.getChildren().add(descBox);

		// the borders of the board

		ArrayList<Line> lines = new ArrayList<>();
		lines.add(new Line(730, 905, 1190, 905));
		lines.add(new Line(725, 180, 1190, 180));
		lines.add(new Line(570, 337, 570, 750));
		lines.add(new Line(1350, 337, 1350, 750));
		lines.add(new Line(1195, 182, 1195 + 150, 182 + 150));
		lines.add(new Line(1195 - 1000 + 377 , 182 + 500 + 70, 1195 + 150 - 1000 + 377 , 182 + 150 + 500 + 70));
		double d = 148; double a = 720; double b = 182;
		lines.add(new Line(a, b, a - d, b + d));
		d = 150;  a = 1195;  b = 903;
		lines.add(new Line(a , b , a + d + 4, b - d));
		for(Line l : lines){
			l.setEffect(new DropShadow(10, Color.DARKGOLDENROD));
			l.setStrokeWidth(8);
			//l.setStroke(Color);
		}
		linesPane.getChildren().addAll(lines);
		gamePane.getChildren().add(linesPane);
		/////////////////////////////////////////////////////////
		//DeckView.show();
		gamePane.getChildren().add(deck);
		 
		
		// home back ground
		Image homeI = new Image("/boardI/homeBackGround.png");
		ImageView v1 = new ImageView(homeI);
		ImageView v2 = new ImageView(homeI);
		ImageView v3 = new ImageView(homeI);
		ImageView v4 = new ImageView(homeI);
		homeBackGround.getChildren().addAll(v1, v2, v3, v4);
		v1.setLayoutX(homeCellsPos[0].x - 40);
		v1.setLayoutY(homeCellsPos[0].y - 17.5);
		v1.setFitWidth(80);
		v1.setPreserveRatio(true);
		v2.setLayoutX(homeCellsPos[1].x - 40);
		v2.setLayoutY(homeCellsPos[1].y - 17.5);
		v2.setFitWidth(80);
		v2.setPreserveRatio(true);
		v3.setLayoutX(homeCellsPos[2].x - 40);
		v3.setLayoutY(homeCellsPos[2].y - 17.5);
		v3.setFitWidth(80);
		v3.setPreserveRatio(true);
		v4.setLayoutX(homeCellsPos[3].x - 40);
		v4.setLayoutY(homeCellsPos[3].y - 17.5);
		v4.setFitWidth(80);
		v4.setPreserveRatio(true);
		gamePane.getChildren().add(homeBackGround);

		// the icons for each player
		InitializeIcons.Icons();
		View.gamePane.getChildren().add(View.iconsWrapper);
		
		
		
		// third : cells image
		cellsPane.getChildren().addAll(cellAccumulator);

		gamePane.getChildren().add(firePitPane);

		gamePane.getChildren().addAll(cellsPane);

		gamePane.getChildren().add(trapCell);
		gamePane.getChildren().addAll(marblesPane);

		// fourth : hands
		gamePane.getChildren().addAll(topPlayer, bottomPlayer, leftPlayer,
				rightPlayer);
		/** cards hands modification **/

		double playerBoxWidth = 480;
		double playerBoxHeight = 150;

		leftPlayer.setPrefSize(playerBoxWidth, playerBoxHeight);
		leftPlayer.setMinSize(playerBoxWidth, playerBoxHeight);
		leftPlayer.setMaxSize(playerBoxWidth, playerBoxHeight);
		leftPlayer.setAlignment(Pos.CENTER);
		rightPlayer.setPrefSize(playerBoxWidth, playerBoxHeight);
		rightPlayer.setMinSize(playerBoxWidth, playerBoxHeight);
		rightPlayer.setMaxSize(playerBoxWidth, playerBoxHeight);
		rightPlayer.setAlignment(Pos.CENTER);
		topPlayer.setPrefSize(playerBoxWidth, playerBoxHeight);
		topPlayer.setMinSize(playerBoxWidth, playerBoxHeight);
		topPlayer.setMaxSize(playerBoxWidth, playerBoxHeight);
		topPlayer.setAlignment(Pos.CENTER);
		bottomPlayer.setPrefSize(playerBoxWidth, playerBoxHeight);
		bottomPlayer.setMinSize(playerBoxWidth, playerBoxHeight);
		bottomPlayer.setMaxSize(playerBoxWidth, playerBoxHeight);
		bottomPlayer.setAlignment(Pos.CENTER);
		StackPane.setAlignment(topPlayer, Pos.TOP_CENTER);
		StackPane.setAlignment(bottomPlayer, Pos.BOTTOM_CENTER);
		StackPane.setAlignment(rightPlayer, Pos.CENTER_RIGHT);
		StackPane.setAlignment(leftPlayer, Pos.CENTER_LEFT);
		leftPlayer.setRotate(90.0);
		rightPlayer.setRotate(-90.0);

		/** board buttons **/
		BoardButtons.play();
		BoardButtons.deselect(primaryStage);
		BoardButtons.music();
		
		/** the second scene background **/

		/** the game **/
		// initializing the number of cards for each hand and the number of
		// cards in firepit;
		UpdateCards.preCardsNumbers = new ArrayList<>();
		for (int i = 0; i < 4; i++)
			UpdateCards.preCardsNumbers.add(0);

		UpdateCards.preFirePitCardsNumbers = 0;

		UpdateCells.update();
		UpdateCards.update();

		ActOnCard.selectCard(primaryStage);
		ActOnMarble.selectMarble(primaryStage);

		PlayGame.play(primaryStage);

		// the game scene
		Scene scene = new Scene(gamePane);

		// the field shortcut
		FieldShortcut.field(scene, primaryStage);

		scene.getStylesheets().add(
				getClass().getResource("style.css").toExternalForm());
		
		
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.show();
		
		
	}

	// play music

	public void playMusic() {
		if (mediaPlayer == null) {
			try {
				String musicFile = "resources/media/backSound.m4a";
				Media media = new Media(new File(musicFile).toURI().toString());
				mediaPlayer = new MediaPlayer(media);
				mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // loop
				mediaPlayer.setVolume(0.5); // 50% volume
				mediaPlayer.play();
			} catch (Exception e) {
				System.err.println("Could not play music: " + e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}