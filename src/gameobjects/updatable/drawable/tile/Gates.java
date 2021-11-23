package gameobjects.updatable.drawable.tile;

import catgame.GameState;
import geometry.Point;
import javafx.scene.image.Image;

/**
 * Creation of the gate that the player must use for there victory. The Gate will be closed until
 * all the keys are collected.
 *
 * @author Rylan Laplante
 */
public class Gates extends Tile {

  private boolean isopen = false;

  /**
   * Constructor that invokes everything needed for the Gate class.
   *
   * @param agameState is important for invoking the parent.
   * @param position is a Point that the gate will be assigned to
   * @param defaultHeat Is used to assign a value to gate
   */
  public Gates(GameState agameState, Point position, double defaultHeat) {
    super(agameState, position, defaultHeat);
  }

  /**
   * This tells if the Gate is a wall or not. If the gate is open is is not, else it is.
   *
   * @return whether the gate should be treated like a wall
   */
  @Override
  boolean isWall() {
    if (isopen) return false;
    else {
      return true;
    }
  }
  /**
   * This tells if the Gate is a floor or not. If the gate is open is is, else it is not.
   *
   * @return whether the gate should be treated like a floor
   */
  @Override
  boolean isFloor() {
    if (isopen) return true;
    else {
      return false;
    }
  }

  /**
   * This method updates the gate it show if it has the necessary keys or not. Once the player has
   * these keys the Gate will open.
   *
   * @param deltaT the time between updates this is (currently set to 1)
   */
  @Override
  public void update(double deltaT) {
    if (theGameState.hasKeys()) {
      if (isopen = false) imageChanged = true;
      isopen = true;
      setImageChanged(true);
    } else {
      isopen = false;
    }
  }
  /** This method states that when the hero enters the Gate, that they win the game. */
  @Override
  void heroEntered() {
    theGameState.heroEscaped();
  }

  /**
   * This method gives the zDepth of the gate. Because its a tile, its zDepth is 1.
   *
   * @return The value of 1 because its a tile.
   */
  @Override
  public int getzDepth() {
    return 4;
  }

  /**
   * Gets the screenimages that is assigned to the gate, during it's 2 states.
   *
   * @return 1 of 2 JPEG & converts them into ScreenImages depending on whether the keys r
   *     collected.
   */
  @Override
  public Image getScreenImage() {

    if (isopen) return theGameState.getImageManager().getOpenGateImage();
    else return theGameState.getImageManager().getClosedGateImage();
  }

  /**
   * This method gets the Screen for Glyph and assigns characters for when the gate is open or
   * close. When the gate is opened it will be marked as an 'O' and when it is Closed it will be
   * marked as a 'X'.
   *
   * @return The character of O if open, as well as the character of X if closed.
   */
  @Override
  public char getScreenGlyph() {
    if (isopen) return 'O';
    else {
      return 'X';
    }
  }
}
