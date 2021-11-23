package gameobjects.updatable.drawable.collidable.pawn;

import catgame.GameState;
import gameobjects.updatable.drawable.tile.Map;
import geometry.Point;
import javafx.scene.image.Image;

/**
 * The enemy Pawn Class. Is a dog, This holds all logic for the creation of Dog.
 *
 * @author Michael Li
 */
public class Dogs extends Pawn {

  private static final double boundsBoxLength =
      .8; // is the sqaure that is the charcter as it moves through space.
  private static double defaultSpeed = .6; // the AI moves slower then the player themselves

  /**
   * This method Dogs brings 2 methods from the Classes GameState & PawnController through the
   * command super()
   *
   * @param aGameState This parameter is the what gets converted to theGameState and is what is
   *     finalized.
   * @param position Is a Point within the map.
   */
  public Dogs(GameState aGameState, Point position) {

    super(aGameState, position, boundsBoxLength, defaultSpeed);
  }
  /**
   * This super invokes the code from two different classes. This method deteremines when
   * individuals get killed.
   */
  public void kill() {

    super.kill();
  }
  /**
   * This method is to determine whether or not the enemy Pawn can enter the Tile.
   *
   * @param Tile This states whether or not the enemy can enter atile.
   * @return a boolean that tells whether or not the enemy can enter.
   */
  public boolean canEnter(Point position) {
    Map theMap = theGameState.getMap();
    if (theMap.isWall(position)) {
      return false;
    } else if (theMap.isFloor(position)) {
      return true;
    }
    return true;
  }

  /**
   * This determine what the outcome with be if the enemy with collides with the Hero. This method
   * will kill the Hero if an enemey touches them.
   */
  @Override
  public void collisionWithHero() {
    Hero theHero = theGameState.getHero();
    theHero.kill();
  }

  /**
   * This provides the zDepth of a dog.
   *
   * @return 4 because dogs should be on top!
   */
  @Override
  public int getzDepth() {
    return 1;
  }

  /**
   * gets the converted Jpeg and puts that image on the screen
   *
   * @return The process of getting the Jpeg onto the screen
   */
  @Override
  public Image getScreenImage() {

    return theGameState.getImageManager().getDogImage();
  }

  /**
   * Gets a speed at which the dog will move at
   *
   * @return the default instance variable so the dog moves slightly slower.
   */
  @Override
  public double getSpeed() {

    return defaultSpeed;
  }
  /**
   * This returns the screen glyph of a dog, a 'd'
   *
   * @return 'd' because its a dog
   */
  @Override
  public char getScreenGlyph() {
    return 'd';
  }
}
