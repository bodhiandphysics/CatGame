package ui;

import catgame.GameState;
import gameobjects.updatable.controller.HeroController;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This class wraps keyboard input for the text based version of the game. In the visual version, it
 * will take keyboard events and dispatch them. It insulates the HeroController, from having to deal
 * with keymapping, and things like quitting the game.
 */
public class KeyboardHandler implements EventHandler<KeyEvent> {

  private HeroController controller;
  private GameState theGameState;
  private boolean keyPressed;
  private KeyCode currentKey = null;
  private final KeyCode UPCHAR = KeyCode.W; // I wanted to use vi keys, but this is more common.
  private final KeyCode DOWNCHAR = KeyCode.S;
  private final KeyCode RIGHTCHAR = KeyCode.D;
  private final KeyCode LEFTCHAR = KeyCode.A;
  private final KeyCode PAUSECHAR = KeyCode.SPACE;
  private final KeyCode QUITCHAR = KeyCode.Q;

  /**
   * We don't want the HeroController to know about the keyboard, so this enum translates between
   * keys, and logical directions*
   */
  public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    NOMOVE,
  }

  /**
   * Constructor, needed to invoke the aGameState
   *
   * @param aGameState this is the parameter used to get theGameState
   */
  public KeyboardHandler(GameState aGameState) {

    theGameState = aGameState;
  }

  /**
   * This sets the Controller type.
   *
   * @param aController is the Controller that is set
   */
  public void setController(HeroController aController) {

    controller = aController;
  }
  /*
   * This is the logic for what events should occur when specific keys are Pressed
   * This handles Keys pressed down This method is used for the movement.
   * It carries an event forward if the condition is met.
   *
   */
  private void handleKeyPressed(KeyEvent event) {

    if (!keyPressed) {
      keyPressed = true;

      switch (event.getCode()) {
        case W:
          controller.handleInput(Direction.UP);
          currentKey = UPCHAR;
          break;

        case S:
          controller.handleInput(Direction.DOWN);
          currentKey = DOWNCHAR;
          break;

        case D:
          controller.handleInput(Direction.RIGHT);
          currentKey = RIGHTCHAR;
          break;

        case A:
          controller.handleInput(Direction.LEFT);
          currentKey = LEFTCHAR;
          break;
      }
    }
  }

  /*
   * This gets when a specific key is Released
   * This is done by conditionals & booleans
   */
  private void handleKeyReleased(KeyEvent event) {

    KeyCode code = event.getCode();
    if (currentKey == code) {
      keyPressed = false;
      controller.handleInput(Direction.NOMOVE);
    }
    if (code == PAUSECHAR) {
      theGameState.getUpdater().togglePause();
      keyPressed = false;
    }
    if (code == QUITCHAR) {
      theGameState.quit();
    }
  }
  /**
   * This checks how to handle when the key is pressed & the conditons behind whether if an event
   * should occur
   */
  public void handle(KeyEvent event) {
    if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
      handleKeyPressed(event);
    }
    if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) handleKeyReleased(event);
  }
}
