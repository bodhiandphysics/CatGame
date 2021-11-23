package gameobjects.updatable.drawable.collidable.collectables;

import catgame.GameState;
import gameobjects.updatable.drawable.collidable.Collidable;
import geometry.Point;

/**
 * This abstract class defines the interface with collectables
 *
 * @author Mingyang Li
 */
public abstract class Collectables extends Collidable {

  private static final double boundsBoxLength = 1;

  /**
   * Abstract class defining collectables
   *
   * @param aGameState the gamestate of the game
   * @param startx xcoord the collectable starts at
   * @param starty ycoord the collectable starts at
   */
  public Collectables(GameState aGameState, Point position) {

    super(aGameState, position, boundsBoxLength);
  }

  /** What happens when the collectable collides with the hero */
  public abstract void collisionWithHero();
}
