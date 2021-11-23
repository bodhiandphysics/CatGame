package catgame;

import gameobjects.updatable.controller.HeroController;
import gameobjects.updatable.drawable.collidable.pawn.Hero;
import java.util.Scanner;
import ui.TextKeyboardHandler;

/**
 * Cat Game is the classic game of running away from dogs, getting treasure, and getting out alive!
 * The hero is a simple feline, trapped in a room with murderous dogs. He has to get out! But to get
 * out, he needs a key, and there's so much treasure lying around... If he gets the key, and gets to
 * the gate, he wins! If he gets hit by a dog... he doesn't win! Controls are with wasd... press
 * enter after every keypress. Currently the game is textual... but that will change...
 */
public class TextCatGame {

  /**
   * Game entry
   *
   * @param args The filename of a map to load.
   */
  public static void main(String[] args) {

    if (args.length != 1) {
      System.out.println("needs a map file");
      System.exit(2);
    }

    String filename = args[0];
    Scanner inputScanner = new Scanner(System.in);
    LevelLoader theLevelLoader = new LevelLoader(null, true);

    GameState theGameState = theLevelLoader.load(filename, 0); // load the level
    int height = theGameState.getMap().getHeight();
    int length = theGameState.getMap().getLength();

    Hero theHero = theGameState.getHero();

    HeroController theHeroController = (HeroController) theHero.getController();

    TextKeyboardHandler input =
        new TextKeyboardHandler(theGameState, theHeroController, inputScanner);

    theGameState.getScreen().update(0); // draw the screen once to start the game;

    input.handleInput(); // handle input is the main game loop as well
  }
}
