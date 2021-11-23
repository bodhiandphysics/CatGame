package ui;

import catgame.GameState;
import gameobjects.updatable.drawable.Drawable;
import geometry.Point;
import java.util.ArrayList;

/**
 * This class implements a screen to draw the game.
 *
 * @author Mingyang Li
 */
public class TextScreen extends Screen {
  private char[][] screenImage;
  private int height;
  private int length;
  private int[][] zBuff;
  private GameState theGameState;
  private ArrayList<Drawable> drawableList;

  /**
   * This is the constructor of the class which takes 4 parameters
   *
   * @param aGameState the gamestate of the cuyrrent game
   * @param height the height of the map
   * @param length the length of the map
   */
  public TextScreen(GameState aGameState, int length, int height) {
    super(aGameState, length, height);
    this.theGameState = aGameState;
    this.height = height;
    this.length = length;
    this.screenImage = new char[length][height];
    this.zBuff = new int[length][height];
    this.drawableList = new ArrayList<>();
  }

  /**
   * This method draws the screen representations of Tiles, Collectable, Cat and Dog at a specific
   * location (x, y) on the map. Each element has its own zDepth which is an integer. The zDepth is
   * used to make sure each element is drawn in the order of Tile(1) > Collectable(2) > Cat(3) >
   * Dog(4).
   *
   * @param glyph the glyph to draw
   * @param x xcoord to draw it
   * @param y ycoord to draw it
   * @param zDepth depth of glyph to deal with draw-over
   */
  public void drawGlyph(char glyph, int x, int y, int zDepth) {
    /**
     * zBuff is a 2D Array with the same size as screenImage, it records the the zDepth of each
     * element at its location zBuff[x][y]. Once current zBuff[x][y] is less than the entered
     * zDepth, we draw the glyph and set the current zBuff[x][y] to new zDepth.
     */
    if (zBuff[x][y] > zDepth) {
      screenImage[x][y] = glyph;
      zBuff[x][y] = zDepth;
    }
  }

  /**
   * This method draws the 2D Array screenImage onto the console. With a call on clearScreen() after
   * drawing one screenImage, it sets the 2D Array zBuff to 0 and ready to draw another screenImage.
   */
  public void drawScreen() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < length; j++) {
        if (j != length - 1) {
          System.out.print(screenImage[j][i]);
        } else {
          System.out.print(screenImage[j][i] + "\n");
        }
      }
    }
  }

  /**
   * This private method set the 2D Array zBuff to 0 so that we are able to draw the next
   * screenImage.
   */
  private void clearScreen() {
    for (int i = 0; i < length; i++) {
      for (int j = 0; j < height; j++) {
        zBuff[i][j] = 10;
      }
    }
  }

  // things I added
  public void update(double deltaT) {
    cullDead();
    Point position;
    for (Drawable object : drawableList) {
      position = object.getPosition();
      drawGlyph(
          object.getScreenGlyph(),
          (int) position.getX(),
          (int) position.getY(),
          object.getzDepth());
    }
    drawScreen();
    clearScreen();
  }

  /** Regiter drawable with the screen */
  public void registerDrawable(Drawable aDrawable) {
    drawableList.add(aDrawable);
  }

  /** Updates the score in super class; overriden here to be a noop, to prevent exception */
  public void updateScore() {}

  private void cullDead() {

    drawableList.removeIf((Drawable object) -> object.isDead());
  }

  /**
   * The following method print out the game state and the current score of the player if they win.
   *
   * @param score
   */
  public void displayWinMessage(int score) {
    System.out.println("You Won");
    System.out.println("Your score is " + score);
  }

  /**
   * The following method print out the game state and the current score of the player if they lose.
   *
   * @param score
   */
  public void displayLooseMessage(int score) {
    System.out.println("You Lost");
    System.out.println("Your score is " + score);
  }
  /**
   * The following methods print out the game state and the current score of the player if they
   * quit.
   *
   * @param score
   */
  public void displayQuitMessage(int score) {
    System.out.println("Goodbye!");
    System.out.println("Your score is " + score);
  }
}
