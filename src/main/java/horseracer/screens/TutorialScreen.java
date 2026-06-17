package horseracer.screens;

import horseracer.app.ScreenManager;
import horseracer.data.DataManager;
import horseracer.model.Level;
import horseracer.service.Session;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Represents the tutorial screen of the game.
 * <p>
 * This screen guides the player through gameplay mechanics,
 * including typing, jumping over obstacles, managing lives,
 * and handling increasing speed. It uses a slide-based system
 * to display information step-by-step.
 *
 * @author Kathy Yao
 * @version 1.0.0
 */
public class TutorialScreen extends AbstractScreen {

    /** The index of the current tutorial slide */
    private int currentSlide = 0;

    /** Titles for each tutorial slide */
    private final String[] titles = {
            "Welcome, Player!",
            "Typing to Jump",
            "Watch Your Lives",
            "Speed Up!"
    };

    /** Descriptions for each tutorial slide */
    private final String[] descriptions = {
            "Welcome to HorseRacer. Your goal is to race your horse as far as possible by typing words correctly.",
            "Obstacles will appear with words above them. Type the word correctly to make your horse JUMP over the obstacle.",
            "If you mistype or don't finish the word in time, you'll hit the obstacle and lose a life. You have 3 lives.",
            "As you race, the horse runs faster! You'll need to type faster to keep up. Good luck!"
    };

    /** Label for displaying the slide title */
    private Label titleLabel;

    /** Label for displaying the slide description */
    private Label descriptionLabel;

    /** Container for visual demonstrations */
    private VBox visualBox;

    /** Button to move to the next slide */
    private Button nextButton;

    /** Button to go back to the previous slide or menu */
    private Button backButton;

    /** Container for slide indicator dots */
    private HBox dotsBox;

    /**
     * Creates the tutorial screen UI and initializes all components.
     *
     * @param screenManager the screen manager used for navigation
     * @param dataManager the data manager for application data
     * @param session the current session storing user state
     */
    public TutorialScreen(ScreenManager screenManager, DataManager dataManager, Session session) {
        super(screenManager, dataManager, session);

        getStylesheets().add(getClass().getResource("/style/tutorial.css").toExternalForm());
        getStyleClass().add("screen");

        try {
            Font.loadFont(getClass().getResourceAsStream("/fonts/VT323-Regular.ttf"), 10);
            Font.loadFont(getClass().getResourceAsStream("/fonts/JetBrainsMono-Regular.ttf"), 10);
        } catch (Exception e) {
            // fallback silently if fonts fail to load
        }

        VBox card = new VBox();
        card.setAlignment(Pos.CENTER);
        card.setSpacing(28);
        card.setMaxWidth(840);
        card.getStyleClass().add("card");

        titleLabel = new Label();
        titleLabel.getStyleClass().add("title");

        descriptionLabel = new Label();
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(640);
        descriptionLabel.setAlignment(Pos.CENTER);
        descriptionLabel.getStyleClass().add("description");

        visualBox = new VBox();
        visualBox.setAlignment(Pos.CENTER);
        visualBox.setMinHeight(120);

        nextButton = new Button();
        nextButton.getStyleClass().add("next-button");
        nextButton.setPrefWidth(250);
        nextButton.setOnAction(e -> goNext());

        dotsBox = new HBox(10);
        dotsBox.setAlignment(Pos.CENTER);

        backButton = new Button("BACK");
        backButton.getStyleClass().add("back-button");
        backButton.setPrefWidth(250);
        backButton.setOnAction(e -> goBack());

        card.getChildren().addAll(titleLabel, descriptionLabel, visualBox, nextButton, dotsBox, backButton);
        setCenter(card);

        updateSlide();
    }

    /**
     * Moves to the previous slide.
     * <p>
     * If the current slide is the first slide, the user is returned
     * to the main menu. Otherwise, the tutorial moves back one step.
     */
    private void goBack() {
        if (currentSlide > 0) {
            currentSlide--;
            updateSlide();
        } else {
            screenManager.showMainMenu();
        }
    }

    /**
     * Moves to the next slide.
     * <p>
     * If the last slide is reached, the user is redirected based on
     * session state:
     * <ul>
     *   <li>If logged in → start gameplay</li>
     *   <li>If not logged in → go to login screen</li>
     * </ul>
     */
    private void goNext() {
        if (currentSlide < titles.length - 1) {
            currentSlide++;
            updateSlide();
        } else {
            if (session.getCurrentUser() != null) {
                screenManager.showGameplay(Level.easy());
            } else {
                screenManager.showLogin();
            }
        }
    }

    /**
     * Updates the UI elements based on the current slide.
     */
    private void updateSlide() {
        titleLabel.setText(titles[currentSlide]);
        descriptionLabel.setText(descriptions[currentSlide]);

        visualBox.getChildren().clear();

        if (currentSlide == 1) {
            visualBox.getChildren().add(createWordDemo());
        } else if (currentSlide == 2) {
            visualBox.getChildren().add(createLivesDemo());
        } else {
            Region spacer = new Region();
            spacer.setMinHeight(100);
            visualBox.getChildren().add(spacer);
        }

        if (currentSlide == titles.length - 1) {
            nextButton.setText("START RACING  →");
        } else {
            nextButton.setText("NEXT  →");
        }

        if (currentSlide == 0) {
            backButton.setText("MAIN MENU");
        } else {
            backButton.setText("BACK");
        }

        updateDots();
    }

    /**
     * Updates the visual indicator dots for slide progress.
     */
    private void updateDots() {
        dotsBox.getChildren().clear();

        for (int i = 0; i < titles.length; i++) {
            Region dot = new Region();
            dot.setPrefSize(16, 16);

            if (i == currentSlide) {
                dot.getStyleClass().add("dot-active");
            } else {
                dot.getStyleClass().add("dot-inactive");
            }

            dotsBox.getChildren().add(dot);
        }
    }

    /**
     * Creates a demonstration showing how typing works.
     *
     * @return a VBox containing the demo
     */
    private VBox createWordDemo() {
        Label instruction = new Label("TYPE THIS WORD:");
        instruction.getStyleClass().add("instructions");

        HBox wordRow = new HBox(0);
        wordRow.setAlignment(Pos.CENTER);

        Label typedPart = new Label("gall");
        typedPart.getStyleClass().add("typed-text");

        Label remainingPart = new Label("op");
        remainingPart.getStyleClass().add("remaining-text");

        wordRow.getChildren().addAll(typedPart, remainingPart);

        VBox box = new VBox(12, instruction, wordRow);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("demo-box");

        return box;
    }

    /**
     * Creates a demonstration showing player lives.
     *
     * @return an HBox containing heart icons
     */
    private HBox createLivesDemo() {
        Label heart1 = createHeart();
        Label heart2 = createHeart();
        Label heart3 = createHeart();

        HBox hearts = new HBox(24, heart1, heart2, heart3);
        hearts.setAlignment(Pos.CENTER);
        return hearts;
    }

    /**
     * Creates a heart icon representing a life.
     *
     * @return a styled heart label
     */
    private Label createHeart() {
        Label heart = new Label("❤");
        heart.getStyleClass().add("heart");
        return heart;
    }
}