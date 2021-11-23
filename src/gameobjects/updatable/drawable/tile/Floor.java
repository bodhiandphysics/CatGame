package gameobjects.updatable.drawable.tile;

import catgame.GameState;
import geometry.Point;
import javafx.scene.image.Image;

/**
 * Subclass of the Class Tile. It sets the empty spots or Floor of the map. These hard the tiles
 * that character move on.
 *
 * @author Rylan Laplante
 */
public class Floor extends Tile {

  /**
   * This is the constructor for Floor.
   *
   * @param agameState gets the Gamestate able to be called.
   * @param position gets the Point class able to be called
   * @param defaultHeat gets the defaultHeat that can be assigned to the floor
   */
  public Floor(GameState agameState, Point position, double defaultHeat) {
    super(agameState, position, defaultHeat);
    // TODO Auto-generated constructor stub
  }
  /**
   * Checks if the tile is a wall. Making sure that no floors can be one. @Return boolean saying it
   * is not a wall
   */
  @Override
  boolean isWall() {
    return false;
  }
  /**
   * Checks if the tile is a floor type. Making sure that all floors are correct
   *
   * @return an true boolean.
   */
  @Override
  boolean isFloor() {
    // TODO Auto-generated method stub
    return true;
  }

  /**
   * This updates what is occuring within the game and loads it into the Updatable Class. Is a noop.
   *
   * @param deltaT time difference between updates
   */
  @Override
  public void update(double deltaT) {}

  /** It checks if the Hero enteres the Floor Tile. */
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
   * Gets the ScreenImage for the floor
   *
   * @return the process of taking the JPEG and making it the ScreenImage
   */
  @Override
  public Image getScreenImage() {

    return theGameState.getImageManager().getFloorImage();
  }
  /**
   * This getScreenGlyph sets the character that we was choosen to represent the Floor
   * tiles. @Return This is the character choosen to represent the Floor tile. Which is a dot '.'
   */
  @Override
  public char getScreenGlyph() {
    return '.';
  }
}
