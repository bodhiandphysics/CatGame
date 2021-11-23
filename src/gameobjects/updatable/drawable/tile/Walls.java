package gameobjects.updatable.drawable.tile;

import catgame.GameState;
import geometry.Point;
import javafx.scene.image.Image;

/**
 * This Class creates the walls for the map. Walls our impassable for both the Hero & the enemy to
 * enter.
 *
 * @author Rylan Laplante
 */
public class Walls extends Tile {

  /**
   * Constructor that invokes everything needed for the Walls class.
   *
   * @param agameState is important for invoking the parent.
   * @param position is a Point that the gate will be assigned to
   * @param defaultHeat Is used to assign a value to gate
   */
  public Walls(GameState aGameState, Point position, double defaultHeat) {
    super(aGameState, position, defaultHeat);
    // TODO Auto-generated constructor stub
  }

  /**
   * This Method isWall() checks if the Wall tile is a Wall.
   *
   * @return This is why it will return true in this Class
   */
  boolean isWall() {
    return true;
  }

  /**
   * This Method isFloorl() checks if the Floor tile is a Floor.
   *
   * @return This is why it will return False in this Class
   *     <p>/*
   */
  boolean isFloor() {
    return false;
  }
  /**
   * This updates what is occuring within the game and loads it into the Updatable Class. In this
   * case a noop.
   *
   * @param deltaT time difference between updates.
   */
  @Override
  public void update(double deltaT) {}

  /** It checks if the Hero enteres the Wall Tile. */
  @Override
  void heroEntered() {}

  /**
   * This provides the zDepth of floor which is need for the Screen class.
   *
   * @return the value of 1 because it is a tile.
   */
  @Override
  public int getzDepth() {
    return 4;
  }
  /**
   * Gets the screenimage that is assigned to the Wall.
   *
   * @return JPEG & converts them into the ScreenImage.
   */
  @Override
  public Image getScreenImage() {

    return theGameState.getImageManager().getWallImage();
  }
  /*
   * This getScreenGlyph sets the character that we was choosen to represent the Wall tiles.
   * @Return This is the character choosen to represent the Wall tile. Which is a dot '*'
   */
  @Override
  public char getScreenGlyph() {
    return '*';
  }
}
