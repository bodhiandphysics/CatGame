package catgame;

import gameobjects.updatable.controller.HeroController;
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.stage.Stage;
import ui.KeyboardHandler;
import ui.Screen;

/**
 * This is the main our main class & Loader of all the classes to launch the game.
 *
 * @author rylanl
 */
public class CatGame extends Application {

  private static String[] levels = {"maps/GateTest.map", "maps/TestingLevel.map"};
  private int nextLevel = 0;
  private int currentScore = 0;
  private GameState currentGameState = null;
  private Stage primaryStage;

  /**
   * Gathers all the necessacy methods & classes in order to launch.
   *
   * @param primaryStage is the parameter that is used to show everything needed to launch once the
   *     main method is completed.
   */
  public void launchLevel() {
    String filename = levels[nextLevel];
    LevelLoader theLevelLoader = new LevelLoader(this, false);
    currentGameState = theLevelLoader.load(filename, currentScore);
    Screen theScreen = currentGameState.getScreen();
    theScreen.showPause();
    Group screenGroup = theScreen.getScreenGroup();
    theScreen.showLevelTitle();
    Scene mainScene = new Scene(screenGroup, theScreen.getLength(), theScreen.getHeight());
    primaryStage.setScene(mainScene);
    KeyboardHandler handler = new KeyboardHandler(currentGameState);
    mainScene.setOnKeyPressed(handler);
    mainScene.setOnKeyReleased(handler);
    handler.setController((HeroController) currentGameState.getHero().getController());
    primaryStage.show();
  }

  // These screens show end of game images
  private void showEndScreen(String endimage, int length, int width) {
    try {
      Group root = new Group();
      Image loseImage = new Image(new FileInputStream(endimage), length, width, true, true);
      ImageView looseView = new ImageView(loseImage);
      root.getChildren().add(looseView);
      Scene scene = new Scene(root, length, width);
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void showLoseScreen() {

    showEndScreen("images/EndScreen.png", 400, 400);
  }

  private void showQuitScreen() {
    showEndScreen("images/EndScreen.png", 400, 400);
  }

  private void showWinScreen() {
    showEndScreen("images/WinScreen.png", 400, 200);
  }

  /** Return to the mainApp to load the next level */
  public void returnToApp() {

    currentScore += currentGameState.getScore();
    if (currentGameState.isLost()) showLoseScreen();
    else if (!currentGameState.isWon()) showQuitScreen();
    else {
      nextLevel++;
      if (nextLevel == levels.length) showWinScreen();
      else launchLevel();
    }
  }

  /** boilerplate for javafx */
  @Override
  public void start(Stage primaryStage) {

    this.primaryStage = primaryStage;
    launchLevel();
  }
  /**
   * This launchs the game.
   *
   * @param args used as the argument that launches the CatGame.
   */
  public static void main(String[] args) {

    if (args.length != 0) levels = args; // use clargs for levels if presented
    launch(args);
  }
}
