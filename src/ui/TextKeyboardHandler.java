package ui;

import catgame.GameState;
import catgame.TextUpdater;
import gameobjects.updatable.controller.HeroController;
import java.util.Scanner;

/**
 * This class wraps keyboard input for the text based version of the game. In the visual version, it
 * will take keyboard events and dispatch them. It insulates the HeroController, from having to deal
 * with keymapping, and things like quitting the game.
 */
public class TextKeyboardHandler extends KeyboardHandler {

  private HeroController controller;
  private Scanner inputScanner;
  private GameState gameState;
  private TextUpdater theUpdater;
  private final String UPCHAR = "w"; // I wanted to use vi keys, but this is more common.
  private final String DOWNCHAR = "s";
  private final String RIGHTCHAR = "d";
  private final String LEFTCHAR = "a";
  private final String QUITCHAR = "q";

  /**
   * We don't want the HeroController to know about the keyboard, so this enum translates between
   * keys, and logical directions*
   */
  public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    NOMOVE
  }

  /**
   * Creates the Keyboard handler, should only be called once.
   *
   * @param aGameState the GameState of the current game.
   * @param acontroller the client HeroController that recieves keyboard input
   * @param ascanner a scanner based on Std.In, or some other stub for testing. Probably shouldn't
   *     be a scanner.
   */
  public TextKeyboardHandler(GameState aGameState, HeroController aController, Scanner ascanner) {
    super(aGameState);
    gameState = aGameState;
    controller = aController;
    inputScanner = ascanner;
    theUpdater = (TextUpdater) gameState.getUpdater();
  }

  /**
   * Converts chars to directions based on a standard map. Currently uses wasd, but we could have
   * this be configurable;
   */
  public void handleInput() {

    /*AAAAAAA!!!!!! Originally a switch, but for some reason wasn't working
    Also, this shouldn't require a return, but just read a byte... but that
    Didn't work either! */
    while (true) {
      theUpdater.handle(1.0);
      String inputLine = inputScanner.nextLine().strip();
      if (inputLine.equals(UPCHAR)) controller.handleInput(KeyboardHandler.Direction.UP);
      else if (inputLine.equals(DOWNCHAR)) controller.handleInput(KeyboardHandler.Direction.DOWN);
      else if (inputLine.equals(RIGHTCHAR)) controller.handleInput(KeyboardHandler.Direction.RIGHT);
      else if (inputLine.equals(LEFTCHAR)) controller.handleInput(KeyboardHandler.Direction.LEFT);
      else if (inputLine.equals(QUITCHAR)) gameState.quit();
      else controller.handleInput(KeyboardHandler.Direction.NOMOVE);
    }
  }
}
