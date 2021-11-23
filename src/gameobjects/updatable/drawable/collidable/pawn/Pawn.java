package gameobjects.updatable.drawable.collidable.pawn;

import catgame.GameState;
import gameobjects.updatable.controller.PawnController;
import gameobjects.updatable.drawable.collidable.Collidable;
import geometry.Point;
import geometry.Square;
import geometry.UnitVector;

/**
 * Class Pawn represents movable objects on the game map, the player avatar, enemies etc. Actual
 * characters are subclasses of Pawn.
 */
public abstract class Pawn extends Collidable {

  private double speed;

  protected PawnController controller;

  /**
   * The constructor produces a new Pawn. The PawnController must be created before hand. Player
   * characters use HeroController. NPCs will use a version of AI
   *
   * @param aGameState The gamestate of the game
   * @param startx xcoord the pawn starts at
   * @param starty ycoord the pawn starts at
   */
  public Pawn(GameState aGameState, Point position, double boundsBoxLength, double speed) {

    super(aGameState, position, boundsBoxLength);
    controller = null;
    this.speed = speed;
  }

  /**
   * Is an abstract method that get the Speed
   *
   * @return the getSpeed for another class. This is abstract so it returns nothing.
   */
  public abstract double getSpeed();

  /**
   * sets the newSpeed
   *
   * @param newSpeed is how fast the pawn can travel
   */
  public void setSpeed(double newSpeed) {

    speed = newSpeed;
  }

  /**
   * gets the proper controller for the character
   *
   * @return The needed controller for the type that the game is wanting
   */
  public PawnController getController() {

    return controller;
  }
  /**
   * set the controller that is being used.
   *
   * @param aController is the variable that is actual controller
   */
  public void setController(PawnController aController) {

    controller = aController;
  }

  /**
   * This function is use by PawnControllers to tell the pawn where to go. Different Pawns may be
   * able to enter different squares, so this functionality is present in Pawn
   *
   * @return a list of tiles the pawn can enter. Currenlty this is just in the 4 cardinal
   *     directions, but this should be changed the 8 tiles surrounding the pawn in a later
   *     iteration.*
   */
  public boolean canGo(UnitVector direction, double distance) {

    boolean canGo = true;

    Square possibleBox = getBoundsBox().translate(direction, distance);

    for (double x = possibleBox.getBottomLeft().getX();
        x <= possibleBox.getBottomRight().getX();
        x++) { // search everysquare in
      for (double y = possibleBox.getTopLeft().getY();
          y <= possibleBox.getBottomLeft().getY();
          y++) {

        canGo &= canEnter(new Point(x, y));
      }
    }
    return canGo;
  }

  /**
   * Different Pawns might be able to enter different tiles (i.e. we could have water tiles that
   * only fish can enter, or the hero with a power up). This method is overrriden in subclasses to
   * provide that functionality
   *
   * @param atile the tile to check for enterability.
   * @return can or cannot enter*
   */
  public abstract boolean canEnter(Point position);

  /**
   * Move the Pawn. Pawns are different then other Collidables by the fact that they can move!
   *
   * @param whereTo the tile to move to
   */
  public void move(UnitVector direction, double distance) {

    if (direction != null) {
      position = position.translate(direction, distance); // I might want to change this
      boundsBox = boundsBox.translate(direction, distance);
      setPositionChanged(true);
    } else {
      setPositionChanged(false);
    }
  }

  /**
   * Pawn's version of Update needs to update their controller. It then moves the pawn. Collisions
   * are checked in gamestate for now.
   *
   * @param deltaT the time difference between updates.
   */
  @Override
  public void update(double deltaT) {

    double distance = getSpeed() * theGameState.getGameSpeed() * deltaT;
    UnitVector nextDirection = controller.getNextDirection(distance);

    move(nextDirection, distance);
  }
}
