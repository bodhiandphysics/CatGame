package gameobjects.updatable.drawable.collidable;

import catgame.GameState;
import gameobjects.updatable.drawable.Drawable;
import gameobjects.updatable.drawable.collidable.pawn.Pawn;
import gameobjects.updatable.drawable.tile.Map;
import geometry.Point;
import geometry.Square;

/**
 * Colidable represrents drawable objects that can collide with the hero, enemies and collectables
 */
public abstract class Collidable extends Drawable {

  protected Map theGameMap;

  protected Square boundsBox;

  private Collider theCollider;

  /**
   * Registers the collidable with gamestate to allow it track collisions
   *
   * @param aGameState the gamestate of the game
   * @param startx xcoord the drawable starts at
   * @param starty ycoord the drawable starts at
   */
  public Collidable(GameState aGameState, Point aPosition, double boundsBoxLength) {

    super(aGameState, aPosition);

    theCollider = theGameState.getCollider();

    theCollider.registerCollidable(this);

    theGameMap = aGameState.getMap();

    boundsBox = new Square(position, boundsBoxLength);
  }

  public Square getBoundsBox() {

    return new Square(boundsBox);
  }
  /**
   * Check if the collidable with collide with a given pawn. This will be replaced using a bounding
   * box approach in the next version
   *
   * @param apawn Does it collide with this pawn
   * @return Does it or does it not collide
   */
  public boolean collidesWith(Pawn aPawn) {
    if (aPawn.getBoundsBox().intersects(boundsBox)) {
      return true;
    }
    return false;
  }

  /**
   * What happens when the collidable collides with the hero pawn. Note because of how collidable is
   * implemented, the hero always collides with itself! In hero, this should be overriden with a
   * noop!
   */
  public abstract void collisionWithHero();
}
