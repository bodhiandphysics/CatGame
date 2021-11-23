package gameobjects.updatable.drawable.tile;

import catgame.GameState;
import gameobjects.updatable.drawable.Drawable;
import geometry.Point;

/**
 * This Class is the logic for creating all the Tiles in the game. There is 3 types of of Tile which
 * will be all subclasses of this document. This creates the Tile that will be add to & used to
 * create with areas of the Map the hero can & can't enter.
 *
 * @author Rylan Laplante
 */
public abstract class Tile extends Drawable {

  private double defaultHeat = 1.0;

  /**
   * Constructor is needed to invoke all the necessacy parent classes for the child.
   *
   * @param agameState is important for invoking the parent.
   * @param position is a Point that the gate will be assigned to
   * @param defaultHeat Is used to assign a value to gate
   */
  public Tile(GameState aGameState, Point position, double defaultHeat) {
    super(aGameState, position);
    this.defaultHeat = defaultHeat;
  }

  /**
   * This is getter method that gets the DefaultHeat for an tile.
   *
   * @return the defaultHeat
   */
  double getDefaultHeat() {
    return defaultHeat;
  }
  /**
   * This is setter method that sets the DefaultHeat for an tile. Used for AI movement
   *
   * @param defaultHeat This is the heat that is used for the AI,.
   */
  void setDefaultHeat(double defaultHeat) {
    this.defaultHeat = defaultHeat;
  }
  /** This is an abstract Method that is used to determine if the Tile is a Wall. */
  abstract boolean isWall();
  /** This is an abstract Method that is used to determine if the Tile is a Floor. */
  abstract boolean isFloor();

  /** This is an abstract Method that is used to determine if the Hero has entered a Tile. */
  abstract void heroEntered();
}
