package gameobjects.updatable.drawable.collidable.pawn;

import catgame.GameState;
import gameobjects.updatable.controller.PawnController;
import gameobjects.updatable.drawable.tile.Map;
import geometry.Point;
import geometry.Square;
import geometry.UnitVector;
import javafx.scene.image.Image;

/**
 * This Class is the features behind the Hero character. Everything for what it looks like,
 * movements, and how it is killed.
 *
 * @author Rylan Laplante
 */
public class Hero extends Pawn {

  private static final double boundsBoxLength = .8; // make it a little smaller than 1
  private static double defaultSpeed = 1;

  /**
   * This constructor lists all the needed from 2 other classes.
   *
   * @param aGameState This is the method that everything that needs to be updataed is assigned to.
   * @param position is the logic behind the Hero's movements.
   */
  public Hero(GameState aGameState, Point position) {
    super(aGameState, position, boundsBoxLength, defaultSpeed);
  }

  /** This is the method that will kill the Hero. By killing the Hero this will end the game. */
  @Override
  public void kill() {

    super.kill();
    theGameState.heroDied();
  }
  /**
   * This method creates the ability to check if the Hero character can enter the tile in which it
   * is trying to move into.
   *
   * @param Tile atile This parameter checks if the Hero can enter a single Tile which it is trying
   *     to move into.
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
   * This method singals for the hero to move into the desired Tile. As well as allows it to
   * logicallydo this by calling other superClasses.
   *
   * @param whereTo which tile to move to
   */
  public void move(UnitVector direction, double distance) {
    super.move(direction, distance);

    Square boundsBox = getBoundsBox();

    for (double x = boundsBox.getBottomLeft().getX();
        x <= boundsBox.getBottomRight().getX();
        x++) { // search everysquare in
      for (double y = boundsBox.getTopLeft().getY(); y <= boundsBox.getBottomLeft().getY(); y++) {
        theGameState.getMap().heroEntered(new Point(x, y));
      }
    }
  }
  /**
   * This is the method for PawnController. Allows the hero to be part of the controller.
   *
   * @return The controller which is used to the movement of the hero.
   */
  public PawnController getController() {

    return controller;
  }

  @Override
  /* This is just an emtpy method becuase the Hero can't collide with itself.*/
  public void collisionWithHero() {}

  /**
   * Give the hero's screen depth. This is created within the Screen Class
   *
   * @return The value of is returned 2 for the hero
   */
  @Override
  public int getzDepth() {

    return 2;
  }

  /**
   * Gets the Hero ScreenImage
   *
   * @return the process of getting the Jpeg into an image on the screen.
   */
  @Override
  public Image getScreenImage() {

    return theGameState.getImageManager().getCatImage();
  }

  /**
   * Gets the Speed for the hero class
   *
   * @return The defaultSpeed that is 1.
   */
  @Override
  public double getSpeed() {

    return defaultSpeed;
  }
  /**
   * Get the screen glyph associated with the hero
   *
   * @return the character 'C'
   */
  @Override
  public char getScreenGlyph() {

    return 'C';
  }
}
